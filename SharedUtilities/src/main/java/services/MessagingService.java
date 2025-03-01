package services;

import model.MessageEntity;
import model.GroupMessageEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessagingService extends Remote {
    void sendMessage(MessageEntity msg) throws RemoteException;
    void sendGroupMessage(GroupMessageEntity msg) throws RemoteException;
}
