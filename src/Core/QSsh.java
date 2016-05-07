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

package Core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
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
        
        if ( null != shell.getConf() )
        {
            qprint.verbose("configuration utilisée:" +
                           "\n\tHost   = " + conf.getIp()+
                           "\n\tLogin  = " + conf.getLogin()+
                           "\n\tPasswd = " + conf.getPasswd());
        }
    }
    
    public boolean isCorrect()
    {
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
        return false;
    }
    
}

class QConf
{
    protected String ip;
    protected String login;
    protected String passwd;
    QPrint qprint = new QPrint("QConf");
    
    public QConf()
    {
        qprint.verbose("created");
    }
    
    public void setIp(String ip)      { this.ip     = ip;   }
    public void setLogin(String name) { this.login  = name; }
    public void setPasswd(String pw)  { this.passwd = pw;   }
    public String getIp()     { return this.ip;     }
    public String getLogin()  { return this.login;  }
    public String getPasswd() { return this.passwd; }
}

class QSshConf extends QConf
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

class QTelnetConf extends QConf
{
    QPrint qprint = new QPrint("QTelnetConf");
    public QTelnetConf()
    {
        qprint.verbose("created");
    }
}

class QSerialConf extends QConf
{
    QPrint qprint = new QPrint("QSerialConf");
    public QSerialConf()
    {
        qprint.verbose("created");
    }
}

class QSshShell extends Shell
{
    private QSshShell shell;
    private QConf     conf;
    private QPrint    qprint = new QPrint("QSshShell");
    
    public QSshShell()
    {
        shell = this;
        shell.setText("Connection setting");
        shell.setLayout(new GridLayout(2, false));
        
        Composite composite = new Composite(this.shell, SWT.NONE);
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
                conf = new QSshConf();
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
                conf = new QTelnetConf();
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
                conf = new QSerialConf();
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
    
    public QConf getConf()
    {
        return this.conf;
    }
    
    protected void checkSubclass()
    {
        // Disable the check that prevents subclassing of SWT components
    }
}
