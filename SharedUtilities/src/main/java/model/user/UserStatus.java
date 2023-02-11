package model.user;
import javafx.scene.paint.Color;

public enum UserStatus {
    AVAILABLE("Available", Color.GREEN),
    BUSY("Busy", Color.RED),
    AWAY("Away", Color.YELLOW),
    OFFLINE("Offline", Color.GRAY);
    private String status;
    private Color color;
    UserStatus(String status, Color color) {
        this.status = status;
        this.color = color;
    }
    public String getStatusName(){return status;}

    public static UserStatus getStatus(String status) {

        if (status.equals("Available"))
            return AVAILABLE;
        if (status.equals("Busy"))
            return BUSY;
        if(status.equals("Away"))
            return AWAY;
        if(status.equals("Offline"))
            return OFFLINE;
        return null;
    }
    public Color getColor() {return color;}
}