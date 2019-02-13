package lcs.fcmtest.model;

import java.util.ArrayList;
import java.util.List;

public class Parent extends Person{
    private List<Children> childrens;

    public Parent(String name, String email, String id, List<Children> childrens) {
        super(name, email, id);
        this.childrens = childrens;
    }

    public List<Children> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Children> childrens) {
        this.childrens = childrens;
    }

    public void addChildren (Children children) {
        if (childrens == null) {
            childrens = new ArrayList<Children>();
        }
        childrens.add(children);
    }
}
