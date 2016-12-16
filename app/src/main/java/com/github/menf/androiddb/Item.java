package com.github.menf.androiddb;

/**
 * Created by menf on 2016-12-15.
 */

public class Item {

    private int _id;
    private int _description;
    private int _name;
    private int _image;

    public Item() {
    }

    public Item(int _id, int _description, int _name, int _image) {
        this._id = _id;
        this._description = _description;
        this._name = _name;
        this._image = _image;
    }

    public Item(int _description, int _name, int _image) {
        this._description = _description;
        this._name = _name;
        this._image = _image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_description() {
        return _description;
    }

    public void set_description(int _description) {
        this._description = _description;
    }

    public int get_name() {
        return _name;
    }

    public void set_name(int _name) {
        this._name = _name;
    }

    public int get_image() {
        return _image;
    }

    public void set_image(int _image) {
        this._image = _image;
    }






}
