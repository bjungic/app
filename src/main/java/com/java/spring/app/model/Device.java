package com.java.spring.app.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    UUID uuid;
    String model;
    LocalDateTime last_time;

    @ManyToMany/*(mappedBy = "devices")*/
    @JoinTable(name = "device_user", joinColumns = @JoinColumn(name = "device_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    final Set<User> users = new HashSet();

    public Device(UUID uuid, String model, LocalDateTime last_time) {
        this.uuid = uuid;
        this.model = model;
        this.last_time = last_time;
    }

    public Device() {
    }

    public Set<User> getUsers() {
        return users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDateTime getLast_time() {
        return last_time;
    }

    public void setLast_time(LocalDateTime last_time) {
        this.last_time = last_time;
    }


    @Override
    public String toString() {
        return "{" +
                "\"uuid\": \"" + uuid + "\"" +
                ", \"model\": \"" + model + "\"" +
                ", \"last_time\": \"" + last_time + "\"" +
//                ", \"users\": \"" + users.iterator().next().getUsername() + "\"" +
                '}';
    }
}
