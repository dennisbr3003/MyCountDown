package com.db5443pr2454563gglmps896rdf3.android.mywidget;

public class SimpleAnswer {
    boolean lAnswer;
    String message;

    public SimpleAnswer() {
    }

    public SimpleAnswer(boolean lAnswer) {
        this.lAnswer = lAnswer;
    }

    public SimpleAnswer(boolean lAnswer, String message) {
        this.lAnswer = lAnswer;
        this.message = message;
    }

    public boolean islAnswer() {
        return lAnswer;
    }

    public void setlAnswer(boolean lAnswer) {
        this.lAnswer = lAnswer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


