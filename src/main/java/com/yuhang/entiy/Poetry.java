package com.yuhang.entiy;

import java.io.Serializable;

/**
 * 唐诗表的实体类
 */
public class Poetry implements Serializable {
    private  Integer id;
    private  String title;
    private  String content;
    private  Poet   poet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Poet getPoet() {
        return poet;
    }

    public void setPoet(Poet poet) {
        this.poet = poet;
    }

    public Poetry() {
        super();
    }

    public Poetry(Integer id, String title, String content, Poet poet) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.poet = poet;
    }

    @Override
    public String toString() {
        return "Poetry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", poet=" + poet +
                '}';
    }
}
