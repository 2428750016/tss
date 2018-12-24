package com.yuhang.entiy;

import java.io.Serializable;

/**
 * 诗人表的实体类
 */
public class Poet implements Serializable {
    private  Integer id;
    private  String  name;

    public Poet() {
        super();
    }

    public Poet(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Poet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
