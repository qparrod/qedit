package Communication;

import Logging.QPrint;

public class QCommConf
{
    protected int    type = QCommType.QUNDEF;
    protected String ip;
    protected String login;
    protected String passwd;
    protected String port;
    QPrint qprint = new QPrint("QConf");
    
    public QCommConf()
    {
        qprint.verbose("created");
    }
    
    public boolean isValid()
    {
        return true;
    }
    
    // setters and getters
    public void setIp(String ip)      { this.ip     = ip;   }
    public void setLogin(String name) { this.login  = name; }
    public void setPasswd(String pw)  { this.passwd = pw;   }
    public String getIp()             { return this.ip;     }
    public String getLogin()          { return this.login;  }
    public String getPasswd()         { return this.passwd; }
    public void setType(int type)     { this.type = type;   }
    public int getType()              { return this.type;   }
    
}
