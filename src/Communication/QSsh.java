// Copyright (C) 2016
//
// This file is part of Qedit.
//
// Qedit is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Qedit is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Qedit.  If not, see <http://www.gnu.org/licenses/>.
//
// author : Quentin Parrod - qparrod@gmail.com

package Communication;


//import com.jcraft.jsch.UIKeyboardInteractive;
//import com.jcraft.jsch.UserInfo;

import Logging.QPrint;

/*
try{
    JSch jsch=new JSch();

    String host="qparrod@localhost";
    String user=host.substring(0, host.indexOf('@'));
    host=host.substring(host.indexOf('@')+1);

    String config =
      "Port 22\n"+
      "\n"+
      "Host foo\n"+
      "  User "+user+"\n"+
      "  Hostname "+host+"\n"+
      "Host *\n"+
      "  ConnectTime 30000\n"+
      "  PreferredAuthentications keyboard-interactive,password,publickey\n"+
      "  #ForwardAgent yes\n"+
      "  #StrictHostKeyChecking no\n"+
      "  #IdentityFile ~/.ssh/id_rsa\n"+
      "  #UserKnownHostsFile ~/.ssh/known_hosts";

    System.out.println("Generated configurations:");
    System.out.println(config);
    ConfigRepository configRepository =
            com.jcraft.jsch.OpenSSHConfig.parse(config);

          jsch.setConfigRepository(configRepository);

          // "foo" is from "Host foo" in the above config.
          Session session=jsch.getSession("foo");

          //String passwd = JOptionPane.showInputDialog("Enter password");
          session.setPassword("saxophone");

          
          UserInfo ui = new MyUserInfo() { };

          session.setUserInfo(ui);
          session.connect(); // making a connection with timeout as defined above.

          Channel channel=session.openChannel("shell");
          channel.setInputStream(System.in);
          channel.setOutputStream(System.out);
          channel.connect(3*1000);
}
catch(Exception e){
  System.out.println(e);
}
*/

/*
 public static abstract class MyUserInfo implements UserInfo, UIKeyboardInteractive{
        public String getPassword(){ return null; }
        public boolean promptYesNo(String str){ return false; }
        public String getPassphrase(){ return null; }
        public boolean promptPassphrase(String message){ return false; }
        public boolean promptPassword(String message){ return false; }
        public void showMessage(String message){ }
        public String[] promptKeyboardInteractive(String destination,
                                String name,
                                String instruction,
                                String[] prompt,
                                boolean[] echo){
        return null;
        }
    }
 */

public class QSsh implements QCommInterface
{
    private QPrint   qprint = new QPrint("QSsh");
    private QSshConf conf;
    
    public QSsh()
    {
        qprint.verbose("QSsh created");
    }
    
    public QSshConf getConf()
    {
        return this.conf;
    }
    
    public void show()
    {
        
    }
    
    public void connect()
    {
        
    }
    
    public void disconnect()
    {
        
    }
    
    public void add()
    {
    }
    
    public boolean isCorrect()
    {
        /*
        if (null!=conf)
        {
            if (conf.getIp().isEmpty()     || 
                conf.getLogin().isEmpty()  || 
                conf.getPasswd().isEmpty() )
            {
                return false;
            }
            
            //if (conf.getIp().matches("*.*.*.*") )
                
            return true;
        }
        */
        return false;
    }
    
}

class QSshConf extends QCommConf
{
    private QPrint qprint = new QPrint("QSshConf");
    
    public QSshConf(String ip)
    {
        this.ip     = ip;
        this.login  = "default";
        this.passwd = "default";
        qprint.verbose("new SSH configuration");
    }
    
    public QSshConf()
    {
        this.ip     = "0.0.0.0";
        this.login  = "default";
        this.passwd = "default";
        qprint.verbose("new SSH configuration");
    }
}
