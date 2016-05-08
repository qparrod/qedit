package Communication;

import org.apache.commons.net.telnet.TelnetClient;

import Logging.QPrint;

public class QTelnet
{
    
}

class QTelnetConf extends QCommConf
{
    QPrint qprint = new QPrint("QTelnetConf");
    public QTelnetConf()
    {
        qprint.verbose("created");
        TelnetClient telnet = new TelnetClient(); 
    }
}