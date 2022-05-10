package com.koley.musrights.misc;

public enum ErrorType {
    INVALID_AUTH("invalid_authentication"),
    INVALID_EMAIL("invalid_email"),
    INVALID_NAME("invalid_name"),
    INVALID_USER("invalid_username"),
    INVALID_PASSWORD("invalid_password"),
    ALREADY_EXISTS("already_exists"),
    INCOMPATIBLE_DATASETS_SIZES("incompatible_datasets_sizes"),
    INCOMPATIBLE_MUSIC_NUMERATION("incompatible_music_numeration");
    private final String templateValue;
    ErrorType(String templateValue)
    {
        this.templateValue = templateValue;
    }

    public String getTemplateValue(){
        return this.templateValue;
    }
}
