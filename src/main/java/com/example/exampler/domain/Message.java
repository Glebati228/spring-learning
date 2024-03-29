package com.example.exampler.domain;

import javax.persistence.*;

@Entity
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    public Message()
    {

    }

    public Message(String text, String tag, User author)
    {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthorName()
    {
        return author != null ? author.getUsername() : "none";
    }

    public User getUser() {
        return author;
    }

    public void setUser(User user) {
        this.author = user;
    }

    public void SetText(String text)
    {
        this.text = text;
    }
    public String GetText()
    {
        return text;
    }

    public void SetTag(String tag)
    {
        this.tag = tag;
    }
    public String GetTag()
    {
        return tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
