
public class SshConfiguration
{
    private static String ip;
    public SshConfiguration()
    {
        System.out.println("New Ssh configuration");
        ip="0.0.0.0";
    }
    public String getIp()
    {
        return ip;
    }
    
    public void save()
    {
        // TODO : store config in a file
        System.out.println("saving config file");
    }
}
