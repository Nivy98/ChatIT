package product.chatit.chatdesc;


public class DisplayChats
{
    private String number,name;

    public DisplayChats(String number, String name)
    {
        this.number = number;
        this.name=name;
    }

    public String getName()
    {
        return name;
    }

    public String getNumber()
    {
        return number;
    }
}
