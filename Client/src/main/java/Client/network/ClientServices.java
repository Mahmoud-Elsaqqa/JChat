package Client.network;


import Client.model.group.Group;
import exceptions.DuplicationUserException;
import model.*;
import exceptions.UserNotFoundException;
import model.group.GroupEntity;
import model.user.UserEntity;
import server.ServerInt;
import server.ServerUserServices;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ClientServices {
    public static UserEntity logIn(String phoneNumber, String password) throws UserNotFoundException, RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerUserServices user = (ServerUserServices) registry.lookup("rmi://localhost:2233/loginService");
            return user.login(new LoginEntity(phoneNumber, password));

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getDuplicated(String phoneNumber) throws RemoteException, DuplicationUserException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerUserServices user = (ServerUserServices) registry.lookup("rmi://localhost:2233/createGroup");
            user.getDuplicated(phoneNumber);

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static GroupEntity createGroup(Group group) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/createGroup");
            return user.createGroup(new GroupEntity(group.getName(), group.getDescription(), group.getOwner_id()));

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<GroupEntity> getUserGroups(int userId) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/getUserGroups");
            return user.getUserGroups(userId);

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void signUp(UserEntity userObject) throws DuplicationUserException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerUserServices user = (ServerUserServices) registry.lookup("rmi://localhost:2233/register");
            user.signUp(userObject);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
