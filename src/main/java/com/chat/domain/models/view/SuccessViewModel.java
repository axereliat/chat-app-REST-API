package com.chat.domain.models.view;

public class SuccessViewModel {

    private String message;

    public SuccessViewModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
