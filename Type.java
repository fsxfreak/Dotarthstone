package com.aplc.dotarthstone;

//unused for now
public abstract class Type
{
    protected int type;

    public void setType(int type) { this.type = type; }
    public abstract void affect(Card origin, Card target);
    public boolean matches(int effect) { return (type & effect) == effect; }
}