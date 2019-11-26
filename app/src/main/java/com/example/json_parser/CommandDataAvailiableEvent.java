package com.example.json_parser;

import java.util.EventObject;

public class CommandDataAvailiableEvent extends EventObject {

    private String message;

    public CommandDataAvailiableEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public CommandDataAvailiableEvent(Object source) {
        this(source, "");
    }

    public CommandDataAvailiableEvent(String s) {
        this(null, s);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[source = " + getSource() + ", message = " + message + "]";
    }
}