package com.chat.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    private Integer id;

    private String text;

    private User author;

    private LocalDateTime sentOn;

    private Chat chat;

    public Message() {
        this.sentOn = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(columnDefinition = "TEXT")
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne
    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Column(name = "sent_on")
    public LocalDateTime getSentOn() {
        return this.sentOn;
    }

    public void setSentOn(LocalDateTime sentOn) {
        this.sentOn = sentOn;
    }

    @ManyToOne
    public Chat getChat() {
        return this.chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
