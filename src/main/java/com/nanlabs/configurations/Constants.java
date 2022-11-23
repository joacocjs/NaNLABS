package com.nanlabs.configurations;

public class Constants {
    public static final String KEY_TOKEN = "key={key}&token={token}";
    //Board endpoints
    public static final String URL_GET_BOARD_LISTS = "https://api.trello.com/1/boards/{idBoard}/lists?"+KEY_TOKEN;
    public static final String URL_GET_BOARD_MEMBERS ="https://api.trello.com/1/boards/{idBoard}/members?"+KEY_TOKEN;
    //List endpoints
    public static final String URL_GET_LIST_CARDS = "https://api.trello.com/1/lists/{idList}/cards?"+KEY_TOKEN;
    //Cards endpoints
    public static final String URL_GET_CARD = "https://api.trello.com/1/cards/{idCard}?"+KEY_TOKEN;
    public static final String URL_POST_CARD = "https://api.trello.com/1/cards?idList={idList}&"+KEY_TOKEN;


    public static final String LABEL_BUG = "Bug";
    public static final String LABEL_MAINTENANCE = "Maintenance";
    public static final String LABEL_RESEARCH = "Research";
    public static final String LABEL_TEST = "Test";

    public static final String[] BUGS_WORDS = {"security","equipment","control","instruction","network","software"};
}
