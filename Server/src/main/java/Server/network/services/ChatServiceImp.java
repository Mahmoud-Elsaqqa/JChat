package Server.network.services;

import Server.business.services.ConnectedService;
import Server.persistance.dao.UserFriendDao;
import exceptions.UserNotFoundException;
import model.FriendEntity;
import model.user.UserStatus;
import services.ChatService;
import services.ClientServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatServiceImp extends UnicastRemoteObject implements ChatService {
    UserFriendDao friendDao = new UserFriendDao();
    public ChatServiceImp() throws RemoteException {
    }

    @Override
    public List<FriendEntity> friendRequest(String sender, List<String> receivers) throws RemoteException {
        List<FriendEntity> requestLST = new ArrayList<>();
            receivers.stream().forEach(
                    (receiver)->{
                        System.out.println("Request send to: " + receiver);
                        try {
                            FriendEntity friendEntity = friendDao.searchByMobileNum(sender);
                            requestLST.add(friendEntity);
                            ClientServices clientServices = ConnectedService.clients.get(receiver);
                            clientServices.friendRequestNotification(friendEntity);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                    }
            );

        System.out.println("Users of friend List:  ");
            requestLST.forEach(u->{
                System.out.println(u);
            });

            return requestLST;
        }

    @Override
    public void acceptFriendRequest(String myNumber, String requestNumber) throws RemoteException {
        friendDao.acceptRequest(myNumber, requestNumber);
    }

    @Override
    public void rejectFriendRequest(String myNumber, String requestNumber) throws RemoteException {
        friendDao.deleteRequest(myNumber, requestNumber);
    }

    @Override
    public FriendEntity searchFriend(String mobile) throws RemoteException {
        try {
            return friendDao.searchByMobileNum(mobile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void tellMyStatusToFriends(String myNumber, UserStatus status) throws RemoteException {
        //1. Update my Status in DB
        // todo make a method to update user status
        //2. Tell my status to friends
        List<FriendEntity> friends = friendDao.getFriendList(myNumber);
        System.out.println("List: " + friends);
        friends.forEach(friend->{
            if(ConnectedService.clients.containsKey(friend.getMobile())){
                try {
                    ConnectedService.clients.get(friend.getMobile()).receiveFriendStatus(myNumber, status);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
