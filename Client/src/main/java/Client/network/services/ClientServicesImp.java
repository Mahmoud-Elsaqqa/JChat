package Client.network.services;

import Client.ui.components.StyledChatMessage;
import Client.ui.controllers.ConversationController;
import Client.ui.controllerutils.ChatType;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import model.FriendEntity;
import model.MessageEntity;
import model.user.UserStatus;
import services.ClientServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServicesImp extends UnicastRemoteObject implements ClientServices {
    public ClientServicesImp() throws RemoteException {
    }

    @Override
    public String getMobile() throws RemoteException {
        System.out.println("FROM User side");
        System.out.println(CurrentUserAccount.getMyAccount().getMobile());
        return CurrentUserAccount.getInstance().getMobile();
    }

    @Override
    public void friendRequestNotification(FriendEntity friend) throws RemoteException {
        System.out.println("Friend Request From : " + friend.getMobile());
        CurrentSession currentSession = CurrentSession.getInstance();
        currentSession.requestsListProperty().add(new Contact(friend));
        System.out.println("request from " + friend.getMobile());

    }

    @Override
    public void receiveMessage(MessageEntity msg) throws RemoteException {

        CurrentSession currentSession = CurrentSession.getInstance();
        Contact senderContact = currentSession.getContactByPhone(msg.getSender());
        ObservableList<MessageEntity> firstTimeChat = FXCollections.observableArrayList();

        StyledChatMessage newStyledMessage = new StyledChatMessage(currentSession.getContactByPhone(msg.getSender()), msg, ChatType.SINGLE);
        if (currentSession.chatsMapProperty().keySet().stream().anyMatch(contact1 -> contact1.getMobile().equals(senderContact.getMobile()))) {
            currentSession.chatsMapProperty().get(senderContact).add(msg);
            currentSession.styledChatMapPropertyProperty().get(currentSession.currentContactChatProperty().get()).add(newStyledMessage);
        }

        else{

                currentSession.chatsMapProperty().put(senderContact, firstTimeChat);
            }

                System.out.println(currentSession.chatsMapProperty().get(senderContact));

                System.out.println("Msg send from: " + msg.getSender() + " Msg body: " + msg.getMSGBody());
            }

            @Override
            public void receiveAnnouncement (String msg) throws RemoteException {
                System.out.println("Announcement from server: " + msg);
            }

            @Override
            public void receiveFriendStatus (String mobile, UserStatus status) throws RemoteException {
                System.out.println("Your friend : " + mobile + " change his status to " + status);
            }


        }
