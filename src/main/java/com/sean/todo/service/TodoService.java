package com.sean.todo.service;


import com.sean.todo.dto.TodoItemDTO;
import com.sean.todo.exception.NotFoundException;
import com.sean.todo.exception.UpdateInvalidException;
import com.sean.todo.model.TodoItem;
import com.sean.todo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.sean.todo.helper.TaskCompleteHelper.handleTodoItemCompletedChanges;

@Service
public class TodoService {

    private final TodoItemRepository todoItemRepository;

    @Autowired
    public TodoService(final TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    public TodoItemDTO getItem(final long id) {
        return todoItemRepository.findById(id)
                .map(TodoItemDTO::new)
                .orElseThrow(itemNotFoundException(id));
    }

    private static Supplier<NotFoundException> itemNotFoundException(final long id) {
        return () -> new NotFoundException("No item found with ID: " + id);
    }

    public List<TodoItemDTO> getItems() {
        return todoItemRepository.findAll()
                .stream()
                .map(TodoItemDTO::new)
                .toList();
    }

    public TodoItemDTO addItem(final TodoItemDTO todoItemDTO) {
        TodoItem todo = new TodoItem();
        todo.setDescription(todoItemDTO.getDescription());
        todo.setComplete(todoItemDTO.isComplete());
        todo.setTargetDate(todoItemDTO.getTargetDate());
        todo.setCreatedDate(new Date());
        todo.setCompletedDate(null);
        return saveAndReturnDTO(todo);
    }

    private TodoItemDTO saveAndReturnDTO(final TodoItem todoItem) {
        TodoItem savedTodo = todoItemRepository.save(todoItem);
        return new TodoItemDTO(savedTodo);
    }

    public TodoItemDTO updateItem(final long id, final TodoItemDTO todoItemDTO) {
        final TodoItem todo = getTodoById(id);
        todo.setDescription(todoItemDTO.getDescription());
        todo.setComplete(todoItemDTO.isComplete());
        todo.setTargetDate(todoItemDTO.getTargetDate());
        todo.setCreatedDate(todoItemDTO.getCreatedDate());
        todo.setCompletedDate(todoItemDTO.getCompletedDate());
        return saveAndReturnDTO(todo);
    }

    private TodoItem getTodoById(long id) {
        return todoItemRepository.findById(id).orElseThrow(itemNotFoundException(id));
    }

    public TodoItemDTO updateItem(final long id, final Map<String, Object> updates) {
        final TodoItem todo = getTodoById(id);

        handleTodoItemCompletedChanges(updates, todo);

        updates.forEach((k, v) -> updateField(k, v, todo));
        return saveAndReturnDTO(todo);
    }

    private void updateField(final String fieldName, final Object fieldValue, final TodoItem todoItem) {
        try {
            final Field field = TodoItem.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(todoItem, fieldValue);

        } catch (final NoSuchFieldException | IllegalAccessException exception) {
            throw new UpdateInvalidException("Field " + " is not updatable.");
        }
    }

    public TodoItemDTO toggleComplete(final long id) {
        final TodoItem todo = getTodoById(id);
        final boolean isNowComplete = !todo.isComplete();
        todo.setComplete(isNowComplete);
        todo.setCompletedDate(isNowComplete ? new Date() : null);
        return saveAndReturnDTO(todo);
    }

    public void deleteItem(final long id) {
        todoItemRepository.deleteById(id);
    }

}
