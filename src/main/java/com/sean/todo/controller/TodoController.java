package com.sean.todo.controller;

import com.sean.todo.dto.TodoItemDTO;
import com.sean.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItemDTO> getItem(@PathVariable(name = "id") final long id) {
        return ResponseEntity.ok(todoService.getItem(id));
    }

    @GetMapping
    public ResponseEntity<List<TodoItemDTO>> getItems() {
        return ResponseEntity.ok(todoService.getItems());
    }

    @PostMapping
    public ResponseEntity<TodoItemDTO> addItem(@RequestBody final TodoItemDTO todoItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.addItem(todoItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItemDTO> updateItem(@PathVariable(name = "id") final long id,
                                                  @RequestBody final TodoItemDTO todoItem) {
        return ResponseEntity.ok(todoService.updateItem(id, todoItem));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoItemDTO> updateItem(@PathVariable(name = "id") final long id,
                                                  @RequestBody final Map<String, Object> updates) {
        return ResponseEntity.ok(todoService.updateItem(id, updates));
    }

    @PatchMapping("/{id}/toggle-complete")
    public ResponseEntity<TodoItemDTO> toggleComplete(@PathVariable(name = "id") final long id) {
        return ResponseEntity.ok(todoService.toggleComplete(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable(name = "id") final long id) {
        todoService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
