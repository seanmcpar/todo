package com.sean.todo.dto;


import com.sean.todo.model.TodoItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemDTO {
    private long id;
    private String description;
    private boolean complete;
    private Date targetDate;
    private Date createdDate;
    private Date completedDate;

    public TodoItemDTO(TodoItem todoItem) {
        this.id = todoItem.getId();
        this.description = todoItem.getDescription();
        this.complete = todoItem.isComplete();
        this.targetDate = todoItem.getTargetDate();
        this.createdDate = todoItem.getCreatedDate();
        this.completedDate = todoItem.getCompletedDate();
    }
}
