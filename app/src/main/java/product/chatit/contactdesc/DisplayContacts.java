package product.chatit.contactdesc;

public class DisplayContacts
{
    private static final String DisplayContacts = DisplayContacts.class.getSimpleName();

    private String phoneNum;
    private String name;
    private String photoURI;

    public DisplayContacts(){

}

    public DisplayContacts(String phoneNum, String name, String photoURI) {
        this.phoneNum = phoneNum;
        this.name = name;
        this.photoURI = photoURI;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoURI(String photoURI) {
        this.photoURI = photoURI;
    }

    public String getPhotoURI() {
        return photoURI;

    }

    public String getPhoneNum() {
        return phoneNum;
    }


    public String getName() {
        return name;
    }



}
