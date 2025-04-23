package UserManagement;

public abstract class User {
    private String userID;
    private String userName;
    private String contactInfo;
    private String gender;
    
    // User constructor
    public User(String userID, String userName, String contactInfo, String gender){
        this.userID = userID;
        this.userName = userName;
        this.contactInfo = contactInfo;
        this.gender = gender;
    }
    
    // Setters and Getters
    public void setUserID(String userID){
        this.userID = userID;
    }
    public void setName(String userName){
        this.userName = userName;
    }
    public void setContactInfo(String contactInfo){
        this.contactInfo = contactInfo;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public String getUserID(){
        return this.userID;
    }
    public String getName(){
        return this.userName;
    }
    public String getContactInfo(){
        return this.contactInfo;
    }
    public String getGender(){
        return this.gender;
    }
    // User's toString method
    @Override
    public String toString() {
        return String.format("User ID: %s\nUser Name: %s\nContact Info:"
                + " %s\nGender: %s",userID, userName, contactInfo, gender);
    }
}