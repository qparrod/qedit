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

package Communication;

import Logging.QPrint;

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

class QCommType
{
    public static int QUNDEF  = -1;
    public static int QSSH    = 0;
    public static int QTELNET = 1;
    public static int QSERIAL = 2;
}



public class QComm
{
    protected boolean   isSsh    = false;
    protected boolean   isTelnet = false;
    protected boolean   isSerial = false;
    private   QCommConf qconf;
    private   QPrint    qprint   = new QPrint("QComm");
    
    public QComm()
    {
        qprint.verbose("created");
    }
    
    public QCommConf getConf()
    {
        return this.qconf;
    }
    
    public void openWindow()
    {
        final Shell shell = new Shell();
        shell.setText("Connection setting");
        shell.setLayout(new GridLayout(2, false));
        
        Composite composite = new Composite(shell, SWT.NONE);
        composite.setLayout(new GridLayout(3, true));
        GridData gridData= new GridData();
        composite.setLayoutData(gridData);
        
        Button btnSsh = new Button(composite, SWT.RADIO);
        btnSsh.setText("SSH");
        gridData= new GridData();
        btnSsh.setLayoutData(gridData);
        btnSsh.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event)
            {
                qprint.verbose("ssh chosen");
                isSsh = true;
            }
        });
        
        Button btnTelnet = new Button(composite, SWT.RADIO);
        btnTelnet.setText("Telnet");
        gridData= new GridData();
        btnTelnet.setLayoutData(gridData);
        btnTelnet.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event)
            {
                qprint.verbose("telnet chosen");
                isTelnet = true;
            }
        });
        
        Button btnSerial = new Button(composite, SWT.RADIO);
        btnSerial.setText("Serial");
        gridData= new GridData();
        btnSerial.setLayoutData(gridData);
        btnSerial.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event)
            {
                qprint.verbose("serial chosen");
                isSerial = true;
            }
        });

        Label lblName = new Label(composite, SWT.NONE);
        lblName.setText("Hostname or IP address");
        gridData= new GridData();
        lblName.setLayoutData(gridData);
        
        final Text textName = new Text(composite, SWT.BORDER);
        gridData= new GridData();
        gridData.horizontalSpan = 2;
        gridData.horizontalAlignment = GridData.FILL;
        textName.setLayoutData(gridData);
        
        Label lblLogin = new Label(composite, SWT.NONE);
        lblLogin.setText("Login");
        gridData= new GridData();
        lblLogin.setLayoutData(gridData);
        
        final Text textLogin = new Text(composite, SWT.BORDER);
        gridData= new GridData();
        gridData.horizontalSpan = 2;
        gridData.horizontalAlignment = GridData.FILL;
        textLogin.setLayoutData(gridData);
        
        Label lblPswd = new Label(composite, SWT.NONE);
        lblPswd.setText("Password");
        gridData= new GridData();
        lblPswd.setLayoutData(gridData);
        
        final Text textPasswd = new Text(composite, SWT.BORDER);
        gridData= new GridData();
        gridData.horizontalSpan = 2;
        gridData.horizontalAlignment = GridData.FILL;
        textPasswd.setLayoutData(gridData);
        
        // TODO : find keyCode for enter. When all is filled,
        //        user can enter to end.
        /*
        textPasswd.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e)
            {
                //if ( (e.keyCode == 115)) 
                //{
                    // TODO : duplicate code from OK button
                    qprint.verbose("OK ");
                    if (null != conf)
                    {
                        conf.setIp(textName.getText());
                        conf.setLogin(textLogin.getText());
                        conf.setPasswd(textPasswd.getText());
                    }
                    shell.dispose();
                //}
            }
        });*/

        Button btnOk = new Button(composite, SWT.NONE);
        btnOk.setText("OK");
        btnOk.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e)
            {
                qprint.verbose("OK ");
                qconf = new QCommConf();
                if (isSsh)    qconf.setType(QCommType.QSSH);
                if (isTelnet) qconf.setType(QCommType.QTELNET);
                if (isSerial) qconf.setType(QCommType.QSERIAL);
                qconf.setIp(textName.getText());
                qconf.setLogin(textLogin.getText());
                qconf.setPasswd(textPasswd.getText());
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
        
        shell.open();
        
        Display display = Display.getCurrent();
        
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
    }
    
    public boolean isSsh()    { return isSsh;    }
    public boolean isTelnet() { return isTelnet; }
    public boolean isSerial() { return isSerial; }
}
