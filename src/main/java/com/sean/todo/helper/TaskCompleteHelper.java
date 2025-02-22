package com.sean.todo.helper;

import com.sean.todo.model.TodoItem;

import java.util.Date;
import java.util.Map;

public class TaskCompleteHelper {

    private TaskCompleteHelper() {
    }

    public static void handleTodoItemCompletedChanges(final Map<String, Object> updates, final TodoItem todoItem) {
        final Boolean isCompleteUpdate = (Boolean) updates.remove("complete");
        final Date completedDateUpdate = (Date) updates.remove("completedDate");

        if (isCompleteUpdate != null || completedDateUpdate != null) {
            applyCompletedItemChanges(todoItem, isCompleteUpdate, completedDateUpdate);
        }
    }

    private static void applyCompletedItemChanges(final TodoItem todoItem,
                                                  final Boolean isCompleteUpdate,
                                                  final Date completedDateUpdate) {
        if (Boolean.TRUE.equals(isCompleteUpdate)) {
            todoItem.setComplete(true);
            todoItem.setCompletedDate(completedDateUpdate != null ? completedDateUpdate :
                    (todoItem.getCompletedDate() == null ? new Date() : todoItem.getCompletedDate()));
        } else if (Boolean.FALSE.equals(isCompleteUpdate)) {
            todoItem.setComplete(false);
            todoItem.setCompletedDate(null);
        } else if (todoItem.isComplete() && completedDateUpdate != null) {
            todoItem.setCompletedDate(completedDateUpdate);
        }
    }
}
