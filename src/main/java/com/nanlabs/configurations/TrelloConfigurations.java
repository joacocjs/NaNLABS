package com.nanlabs.configurations;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "trello")
public class TrelloConfigurations {
    @Value("${trello.secret}")
    private String secret;
    @Value("${trello.appkey}")
    private String appkey;
    @Value("${trello.token}")
    private String token;
    @Value("${trello.board}")
    private String board;
    @Value("${trello.board.label.bug}")
    private String bugLabel;
    @Value("${trello.list.todo}")
    private String todoList;
    @Value("${trello.list.tasks}")
    private String tasksList;
    @Value("${trello.board.label.maintenance}")
    private String maintenanceLabel;
    @Value("${trello.board.label.research}")
    private String researchLabel;
    @Value("${trello.board.label.test}")
    private String testLabel;


    public void setClientSecret(String secret){
        this.secret = secret;
    }
    public void setAppkey(String appkey){
        this.appkey = appkey;
    }
    public void setToken(String token){  this.token = token;}
    public void setBoard(String board){ this.board = board; }
    public void setTasksList(String tasksList){ this.tasksList = tasksList; }
    public void setTodoList(String todoList){ this.todoList = todoList; }
    public void setBugLabel(String bugLabel){ this.bugLabel = bugLabel; }
    public void setMaintenanceLabel(String maintenanceLabel){ this.maintenanceLabel = maintenanceLabel; }
    public void setResearchLabel(String researchLabel){ this.researchLabel = researchLabel; }
    public void setTestLabel(String testLabel){ this.testLabel = testLabel; }

    public String getClientSecret(){
        return secret;
    }
    public String getAppkey(){
        return appkey;
    }
    public String getToken(){
        return token;
    }
    public String getBoard(){
        return board;
    }
    public String getTasksList(){
        return tasksList;
    }
    public String getTodoList(){
        return todoList;
    }
    public String getBugLabel(){
        return bugLabel;
    }
    public String getMaintenanceLabel(){
        return maintenanceLabel;
    }
    public String getResearchLabel(){
        return researchLabel;
    }
    public String getTestLabel(){
        return testLabel;
    }
}
