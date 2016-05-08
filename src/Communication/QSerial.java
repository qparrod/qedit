package Communication;

import Logging.QPrint;

public class QSerial
{
    
}

class QSerialConf extends QCommConf
{
    QPrint qprint = new QPrint("QSerialConf");
    public QSerialConf()
    {
        qprint.verbose("created");
    }
}