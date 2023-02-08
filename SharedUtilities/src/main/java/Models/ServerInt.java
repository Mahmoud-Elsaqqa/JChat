package Models;

import Models.ClientInt;
import Models.LoginEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInt extends Remote {
    void login(LoginEntity name) throws RemoteException;
    String logout(String name) throws RemoteException;
    String connect(ClientInt client) throws RemoteException;
    String disconnect(ClientInt client) throws RemoteException;
}
