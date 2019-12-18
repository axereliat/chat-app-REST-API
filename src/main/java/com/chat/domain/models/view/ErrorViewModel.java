package com.chat.domain.models.view;

import java.util.List;

public class ErrorViewModel {

    private List<String> errors;

    public ErrorViewModel(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
