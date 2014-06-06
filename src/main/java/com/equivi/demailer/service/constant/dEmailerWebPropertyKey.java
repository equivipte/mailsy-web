package com.equivi.demailer.service.constant;


public enum dEmailerWebPropertyKey {

    PAGING_MAX_RECORDS("paging.max.records"),
    FORGOT_PASSWORD_EMAIL_SUBJECT("forgot.password.email.subject"),
    PASSWORD_CHANGED_EMAIL_SUBJECT("password.changed.email.subject"),
    NEW_USER_EMAIL_SUBJECT("new.user.email.subject"),
    EMAIL_VERIFIER_API_KEY("email.verifier.api.key"),
    EMAIL_VERIFIER_API_TIMEOUT("email.verifier.api.timeout"),
    EMAIL_VERIFIER_URL("email.verifier.url");


    private String keyName;

    dEmailerWebPropertyKey(String keyName) {

        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}