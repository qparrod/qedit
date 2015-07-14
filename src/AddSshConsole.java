import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class AddSshConsole extends Shell
{
    private static SshConfiguration sshconfig;
    public boolean exit;
    private static AddSshConsole shell;
    
    /**
     * Launch the application.
     * @param args
     */
    public static void main(String args[])
    {
        try
        {
            Display display = Display.getDefault();
            shell = new AddSshConsole(display);
            shell.open();
            shell.layout();
            while (!shell.isDisposed())
            {
                if (!display.readAndDispatch())
                {
                    display.sleep();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Create the shell.
     * @param display
     */
    public AddSshConsole(Display display)
    {
        super(display, SWT.SHELL_TRIM);
        setLayout(new FormLayout());
        
        Label lblName = new Label(this, SWT.NONE);
        FormData fd_lblName = new FormData();
        lblName.setLayoutData(fd_lblName);
        lblName.setText("Name");
        
        Label lblPort = new Label(this, SWT.NONE);
        fd_lblName.bottom = new FormAttachment(lblPort, -6);
        fd_lblName.left = new FormAttachment(lblPort, 0, SWT.LEFT);
        FormData fd_lblPort = new FormData();
        fd_lblPort.left = new FormAttachment(0, 10);
        lblPort.setLayoutData(fd_lblPort);
        lblPort.setText("Port");
        
        Label lblLogin = new Label(this, SWT.NONE);
        fd_lblPort.bottom = new FormAttachment(lblLogin, -10);
        FormData fd_lblLogin = new FormData();
        fd_lblLogin.left = new FormAttachment(0, 10);
        lblLogin.setLayoutData(fd_lblLogin);
        lblLogin.setText("Login");
        
        Label lblPassword = new Label(this, SWT.NONE);
        fd_lblLogin.bottom = new FormAttachment(lblPassword, -6);
        FormData fd_lblPassword = new FormData();
        fd_lblPassword.left = new FormAttachment(0, 10);
        fd_lblPassword.bottom = new FormAttachment(100, -23);
        lblPassword.setLayoutData(fd_lblPassword);
        lblPassword.setText("Password");
        
        Button btnTelnet = new Button(this, SWT.RADIO);
        FormData fd_btnTelnet = new FormData();
        fd_btnTelnet.top = new FormAttachment(0, 10);
        fd_btnTelnet.left = new FormAttachment(0, 24);
        btnTelnet.setLayoutData(fd_btnTelnet);
        btnTelnet.setText("Telnet");
        
        Button btnSsh = new Button(this, SWT.RADIO);
        FormData fd_btnSsh = new FormData();
        fd_btnSsh.bottom = new FormAttachment(btnTelnet, 0, SWT.BOTTOM);
        fd_btnSsh.left = new FormAttachment(btnTelnet, 15);
        btnSsh.setLayoutData(fd_btnSsh);
        btnSsh.setText("SSH");
        
        Button btnSerial = new Button(this, SWT.RADIO);
        FormData fd_btnSerial = new FormData();
        fd_btnSerial.bottom = new FormAttachment(btnTelnet, 0, SWT.BOTTOM);
        fd_btnSerial.left = new FormAttachment(btnSsh, 24);
        btnSerial.setLayoutData(fd_btnSerial);
        btnSerial.setText("Serial");
        
        Button btnOk = new Button(this, SWT.NONE);
        btnOk.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                System.out.println("OK");
                // save configuration
                SshConfiguration sshconf = new SshConfiguration();
                System.out.println("IP:" + sshconf.getIp());
                sshconf.save();
                exit = true;
                shell.dispose();
                //System.exit(0);
            }
        });
        FormData fd_btnOk = new FormData();
        btnOk.setLayoutData(fd_btnOk);
        btnOk.setText("OK");
        
        Button btnCancel = new Button(this, SWT.NONE);
        fd_btnOk.bottom = new FormAttachment(btnCancel, 0, SWT.BOTTOM);
        fd_btnOk.left = new FormAttachment(btnCancel, 6);
        FormData fd_btnCancel = new FormData();
        fd_btnCancel.bottom = new FormAttachment(100, -10);
        fd_btnCancel.left = new FormAttachment(lblPassword, 283);
        btnCancel.setLayoutData(fd_btnCancel);
        btnCancel.setText("Cancel");
        createContents();
    }
    
    /**
     * Create contents of the shell.
     */
    protected void createContents()
    {
        setText("Add Machine");
        setSize(450, 300);
        
    }
    
    @Override
    protected void checkSubclass()
    {
        // Disable the check that prevents subclassing of SWT components
    }
}
