package com.java.iti.utils;

import com.java.iti.utils.interfaces.ClientInt;
import com.java.iti.utils.interfaces.Message;
import com.java.iti.utils.interfaces.ServerInt;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIConnection {
    public static void connect(){
        try {
            ServerInt user = (ServerInt)Naming.lookup("rmi://localhost:2233/startconnect");
            ClientInt clientInt = new Message();
            user.connect(clientInt);
            System.out.println(user.login("Ahmed"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
