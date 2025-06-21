package com.github.hotechbackend.task.domain.model.aggregates;

import com.github.hotechbackend.shared.domain.aggregates.AuditableAbstractAggregateRoot;
import com.github.hotechbackend.task.domain.model.valueobjects.RoomIdentifier;
import com.github.hotechbackend.task.domain.model.valueobjects.TaskInformation;
import com.github.hotechbackend.task.domain.model.valueobjects.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.datetime.DateFormatter;

import java.util.Date;
import java.util.Locale;

@Entity
public class Task extends AuditableAbstractAggregateRoot<Task> {

    @Embedded
    @Getter
    private TaskInformation taskInformation;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false)
    @Getter
    private Date dueDate;

    @Embedded
    @Getter
    private RoomIdentifier room;

    public Task() {

    }

    public Task(TaskInformation taskInformation, TaskStatus status, Date dueDate,RoomIdentifier room) {
        this.taskInformation = taskInformation;
        this.status = status;
        this.dueDate = dueDate;
        this.room = room;
    }

    public void complete() {
        if (this.isCompleted()) {
            throw new IllegalStateException("Task is already completed");
        }
        this.status = TaskStatus.COMPLETED;
    }

    public void start() {
        if (this.isCompleted()) {
            throw new IllegalStateException("Task is already completed");
        }
        this.status = TaskStatus.IN_PROGRESS;
    }

    public boolean isCompleted() {
        return this.status == TaskStatus.COMPLETED;
    }

    public boolean isInProgress() {
        return this.status == TaskStatus.IN_PROGRESS;
    }

    public boolean isExpired() {
        return this.dueDate.before(new Date());
    }

    public String getNiceDueDate() {
        DateFormatter dateFormatter = new DateFormatter("dd MMM yyyy, HH:mm");
        Locale locale = Locale.ENGLISH;
        return dateFormatter.print(this.dueDate, locale);
    }

    public TaskStatus getStatus() {
        return status;
    }

}
