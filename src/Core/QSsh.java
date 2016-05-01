package Core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Logging.QPrint;

public class QSsh
{
    private QPrint qprint = new QPrint("QSsh");
    //private List list = new LinkedList();
    
    public QSsh()
    {
        qprint.verbose("QSsh created");
    }
    
    public void add()
    {
        qprint.verbose("Add machine");
        Display display = Display.getDefault();
        QSshShell shell = new QSshShell();
        shell.open();
        shell.layout();
        
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        
        if (shell.isConfForSsh())
        {
            QSshConf conf = shell.getSshConf();
            qprint.verbose("configuration utilisée:" +
                           "\n\tHost   = " + conf.getIp()+
                           "\n\tLogin  = " + conf.getLogin()+
                           "\n\tPasswd = " + conf.getPasswd());
            //list.add(shell.getSshConf());
        }
    }
    
    
}

class QSshConf
{
    private String ip;
    private String login;
    private String passwd;
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
    
    public void setIp(String ip)      { this.ip     = ip;   }
    public void setLogin(String name) { this.login  = name; }
    public void setPasswd(String pw)  { this.passwd = pw;   }
    public String getIp()     { return this.ip;     }
    public String getLogin()  { return this.login;  }
    public String getPasswd() { return this.passwd; }
}

class QSshShell extends Shell
{
    private QSshShell shell;
    private QSshConf  conf;
    private QPrint    qprint = new QPrint("QSshShell");
    
    public QSshShell()
    {
        shell = this;
        shell.setText("Connection setting");
        shell.setLayout(new GridLayout(2, false));
        
        Composite composite = new Composite(this.shell, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        GridData gridData= new GridData();
        composite.setLayoutData(gridData);
        
        Button btnSsh = new Button(composite, SWT.RADIO);
        btnSsh.setText("SSH");
        gridData= new GridData();
        gridData.horizontalSpan = 2;
        btnSsh.setLayoutData(gridData);
        btnSsh.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event)
            {
                qprint.verbose("ssh chosen");
                conf = new QSshConf();
            }
        });

        Label lblName = new Label(composite, SWT.NONE);
        lblName.setText("Hostname or IP address");
        gridData= new GridData();
        lblName.setLayoutData(gridData);
        
        final Text textName = new Text(composite, SWT.BORDER);
        gridData= new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        textName.setLayoutData(gridData);
        
        Label lblLogin = new Label(composite, SWT.NONE);
        lblLogin.setText("Login");
        gridData= new GridData();
        lblLogin.setLayoutData(gridData);
        
        final Text textLogin = new Text(composite, SWT.BORDER);
        gridData= new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        textLogin.setLayoutData(gridData);
        
        Label lblPswd = new Label(composite, SWT.NONE);
        lblPswd.setText("Password");
        gridData= new GridData();
        lblPswd.setLayoutData(gridData);
        
        final Text textPasswd = new Text(composite, SWT.BORDER);
        gridData= new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        textPasswd.setLayoutData(gridData);

        Button btnOk = new Button(composite, SWT.NONE);
        btnOk.setText("OK");
        btnOk.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e)
            {
                qprint.verbose("OK ");
                if (null != conf)
                {
                    conf.setIp(textName.getText());
                    conf.setLogin(textLogin.getText());
                    conf.setPasswd(textPasswd.getText());
                }
                shell.dispose();
            }
        });
        
        Button btnCancel = new Button(composite, SWT.NONE);
        btnCancel.setText("Cancel");
        btnCancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e)
            {
                qprint.verbose("Cancel");
                shell.dispose();
            }
        });
    }
    
    public QSshConf getSshConf()
    {
        return this.conf;
    }
    
    public boolean isConfForSsh()
    {
        if (null != this.conf)
        {
            return true;
        }
        return false;
    }
    
    protected void checkSubclass()
    {
        // Disable the check that prevents subclassing of SWT components
    }
}
