package main.java.com.tigratius.airticketofficeconsole.model;

public enum Message {

    LINE("----------------------------------------------"),
    ERROR_INPUT("Неправильный ввод. Повторите попытку!"),
    EMPTY_LIST("Список пуст"),
    SUCCESSFUL_OPERATION("Успешная операция"),
    ERROR_OPERATION("Ошибка!"),
    NOT_FIND_ID("Нет ID = "),
    INPUT_DATE("Введите дату в формате yyyy-MM-dd:"),
    INPUT_DEPARTURE("Введите страну вылета:"),
    INPUT_ARRIVAL("Введите страну прилета:");

    private final String message;

     Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
