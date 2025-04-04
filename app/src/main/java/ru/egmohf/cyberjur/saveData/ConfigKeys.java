package ru.egmohf.cyberjur.saveData;

public enum ConfigKeys {
    BG_FIRST("first-bg-color", "#383C47"),
    BG_SECOND("second-bg-color", "#272B34"),
    RED("red", "#FF4342"),
    BLUE("blue", "#3472FB"),
    WHITE("white", "#FEFEFE"),
    GREEN("green", "#2FFB78"),
    GREY("grey", "#5D6C89"),
    BLACK("black", "#000000"),
    YELLOW("yellow", "#FFD700"),
    API_URL("api_url","https://school.kiberlandia.ru/api/");


    private final String key;
    private final String defaultValue;

    ConfigKeys(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
