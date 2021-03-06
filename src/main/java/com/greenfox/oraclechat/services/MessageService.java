package com.greenfox.oraclechat.services;

import com.greenfox.oraclechat.OraclechatApplication;
import com.greenfox.oraclechat.model.Client;
import com.greenfox.oraclechat.model.IncomingClientMessage;
import com.greenfox.oraclechat.model.Message;
import com.greenfox.oraclechat.model.Status;
import com.greenfox.oraclechat.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageService {

    @Autowired
    MessageRepo messageRepo;

    public Iterable<Message> listAll() {
        return messageRepo.findAll();
    }

    public Iterable<Message> listTenMostRecent() {
        return messageRepo.listTenMostRecent();
    }

    public Message getMessage(long id) {
        return messageRepo.findOne(id);
    }

    public void addMessage (Message m) {
        messageRepo.save(m);
    }

    public Status sendMessage(Message m, Client c) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<IncomingClientMessage> request = new HttpEntity<>(new IncomingClientMessage(m, c));
        Status response = restTemplate.postForObject(OraclechatApplication.CHAT_APP_PEER_ADDRESS, request, Status.class);
        return response;
    }
}