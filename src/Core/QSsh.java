package Core;

import org.eclipse.swt.widgets.Display;

public class QSsh
{
    private String configurationFile;
    private String list
    
    public QSsh()
    {
        System.out.println("QSsh created");
    }
    
    public void add()
    {
        System.out.println("Add machine");
        AddSshConsole ssh = new AddSshConsole(Display.getDefault());
        ssh.open();
    }
}
