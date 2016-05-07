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

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.graphics.Point;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

import Logging.QPrint;

//////////////////////////////////////////////////////
// GENERAL FEATURES TO DEVELOP
//   - add doxygen code
//   - add configuration file
//
// DEV FEATURES
//   - ssh/telnet connection to machine
//   - add/del files in tree
//   - shortcuts implementation


class QPerspective
{
    public static boolean DEV  = true;
    public static boolean NORM = true;
}

public class Playground
{
    //private static Text    txtTest;
    private static Shell   shlQeditef;
    private static Display display;
    private static Text    text_1;
    public  static String  ROOT;
    private static Tree    tree_2;
    private static Path    fileSelected;

    public static void main(String[] args)
    {
        final QPrint qprint = new QPrint("Main");
        qprint.verbose("qedit debug mode");
        display = Display.getDefault();
        shlQeditef = new Shell();
        shlQeditef.setLayout(new GridLayout(1, false));
        shlQeditef.setText("Qedit");
        
        Composite composite = new Composite(shlQeditef, SWT.NONE);
        composite.setLayout(new GridLayout(1, true));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        qprint.verbose("created composite (general)");
        
        TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
        tabFolder.setLayout(new GridLayout(1, true));
        tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        qprint.verbose("created tabFolder");
        
        Composite composite_3 = new Composite(tabFolder, SWT.NONE);
        int numColums = 4;
        composite_3.setLayout(new GridLayout(numColums, false));
        GridData gridData = new GridData();
        gridData.verticalAlignment = GridData.FILL;
        composite_3.setLayoutData(gridData);
        qprint.verbose("created composite_3");
        
        if ( System.getProperty("os.name").startsWith("Linux") )
        {
            ROOT = "/home/qparrod/workspace/qedit/store/";
        }
        else if ( System.getProperty("os.name").startsWith("Windows") )
        {
            ROOT = "G:/repo_qedit/store/";
        }
        
        //////////////////////////////////////////////////////////////////////////////////
        // ITEM 1
        //////////////////////////////////////////////////////////////////////////////////
        TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
        tabItem.setText("Notes");
        tabItem.setControl(composite_3);
        
        //////////////
        // BUTTONS
        //////////////
        final QFileManager fileManager = new QFileManager(ROOT);
        SelectionAdapter buttonSelectionAdapter = new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event)
            {
                // save file selected in item list
                qprint.verbose(event.widget.getClass().getSimpleName());
                if (event.widget.toString().contains("Open"))
                {
                    fileManager.open();
                }
                else if (event.widget.toString().contains("Add"))
                {
                    fileManager.add();
                }
                else if (event.widget.toString().contains("Del"))
                {
                    fileManager.del();
                }
                else
                {
                    qprint.error("button not exists!");
                }
            }
        };
        Button buttonOpen = new Button(composite_3, SWT.NONE);
        buttonOpen.setText("Open");
        buttonOpen.addSelectionListener(buttonSelectionAdapter);
        gridData = new GridData();
        buttonOpen.setLayoutData(gridData);
        Button buttonAdd = new Button(composite_3, SWT.NONE);
        buttonAdd.setText("Add");
        buttonAdd.addSelectionListener(buttonSelectionAdapter);
        gridData = new GridData();
        buttonAdd.setLayoutData(gridData);
        Button buttonDelete = new Button(composite_3, SWT.NONE);
        buttonDelete.setText("Delete");
        buttonDelete.addSelectionListener(buttonSelectionAdapter);
        gridData = new GridData();
        buttonDelete.setLayoutData(gridData);
        
        //////////////
        // TEXT PART
        //////////////
        text_1 = new Text(composite_3, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        text_1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e)
            {
                if ( (e.stateMask & SWT.CTRL) != 0 && (e.keyCode == 115)) 
                {
                    if ( null != fileSelected)
                    {
                        fileManager.save(fileSelected, text_1);
                    }
                }
            }
        });
        
        gridData = new GridData();
        gridData.verticalAlignment         = GridData.FILL;
        gridData.horizontalAlignment       = GridData.FILL;
        gridData.verticalSpan              = 2;
        gridData.grabExcessVerticalSpace   = true;
        gridData.grabExcessHorizontalSpace = true;
        text_1.setLayoutData(gridData);
        
        //////////////
        // TREE PART
        //////////////
        tree_2 = new Tree(composite_3, SWT.SINGLE | SWT.BORDER);
        
        // open recursively files already present in software repository
        new QTree(tree_2);
        
        tree_2.addMouseListener(new MouseAdapter() {
            // TODO: create a handler here
            public void mouseDown(MouseEvent e)
            {
                int buttonType = e.button;
                switch(buttonType)
                {
                    case 1: // left click
                    {
                        //TODO : put it in a function handler
                        TreeItem dest = tree_2.getItem(new Point(e.x, e.y));
                        if (null == dest) { return; }
                        Path path = Paths.get(findAbsolutePath(dest, dest.getText()));
                        qprint.verbose("left button clicked, selected path="+ path.toString());
                        fileSelected = path;
                        break;
                    }
                    case 3: // right click
                    {
                        qprint.verbose("right button clicked");
                        // swing lib to open window
                        break;
                    }
                }
            }
            public String findAbsolutePath(TreeItem tree, String path)
            {
                if ( null == tree.getParentItem() ) { return (ROOT + path); }
                String parent = tree.getParentItem().getText();
                return findAbsolutePath(tree.getParentItem(), parent + "/"+ path);
            }
            public void mouseDoubleClick(MouseEvent event)
            {
                if ( tree_2 == null ) { return; }
                TreeItem dest = tree_2.getItem(new Point(event.x, event.y));
                if ( dest == null ) { return; }
                Path path = Paths.get(findAbsolutePath(dest, dest.getText()));
                qprint.verbose("open file "+ path.toString());
                fileManager.open(path, text_1);
            }
        });
        tree_2.setTouchEnabled(true);
        gridData = new GridData();
        gridData.horizontalSpan            = 3;
        gridData.verticalAlignment         = GridData.FILL;
        gridData.horizontalAlignment       = GridData.FILL;
        gridData.grabExcessVerticalSpace   = true;
        gridData.grabExcessHorizontalSpace = false;
        tree_2.setLayoutData(gridData);
        
        
        
        
        //////////////////////////////////////////////////////////////////////////////////
        // ITEM 2
        //////////////////////////////////////////////////////////////////////////////////
        TabItem tbtmMachines = new TabItem(tabFolder, SWT.NONE);
        tbtmMachines.setText("Machines");
        
        Composite composite_1 = new Composite(tabFolder, SWT.NONE);
        tbtmMachines.setControl(composite_1);
        composite_1.setLayout(new GridLayout(2, false));
        
        final QSsh qssh = new QSsh();
        
        final Button btnAddConf   = new Button(composite_1, SWT.NONE          );
        final Text   txtTest      = new Text  (composite_1, SWT.BORDER        | 
                                                            SWT.READ_ONLY     | 
                                                            SWT.H_SCROLL      | 
                                                            SWT.V_SCROLL      | 
                                                            SWT.CANCEL        | 
                                                            SWT.MULTI         );
        final Tree   tree         = new Tree  (composite_1, SWT.BORDER        |
                                                            SWT.CHECK         | 
                                                            SWT.FULL_SELECTION);
        
        /////////////
        // BUTTONS
        /////////////
        btnAddConf.setText("Add Machine");
        btnAddConf.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event)
            {
                // add a new SSH configuration by adding a new machine in list
                qssh.add();
                if (!qssh.isCorrect())
                {
                    qprint.error("SSH configuration is not good");
                    return;
                }
                
                QSshConf conf = qssh.getConf();
                TreeItem trtmMachine = new TreeItem(tree, 0);
                trtmMachine.setText(conf.getIp());
            }
        });
        gridData = new GridData();
        btnAddConf.setLayoutData(gridData);
        
        /////////////
        // TEXT
        /////////////
        txtTest.setText("window to display debug text");
        gridData = new GridData();
        gridData.horizontalSpan            = 1;
        gridData.verticalSpan              = 2;
        gridData.verticalAlignment         = GridData.FILL;
        gridData.horizontalAlignment       = GridData.FILL;
        gridData.grabExcessVerticalSpace   = true;
        gridData.grabExcessHorizontalSpace = true;
        txtTest.setLayoutData(gridData);
        
        /////////////
        // TREE
        /////////////
        gridData = new GridData();
        gridData.verticalAlignment         = GridData.FILL;
        gridData.horizontalAlignment       = GridData.FILL;
        gridData.grabExcessVerticalSpace   = true;
        gridData.grabExcessHorizontalSpace = false;
        tree.setLayoutData(gridData);
        
        //////////////////////////////////////////////////////////////////////////////////
        // ITEM 3
        //////////////////////////////////////////////////////////////////////////////////
        if ( QPerspective.DEV )
        {
            Composite composite_dev = new Composite(tabFolder, SWT.NONE);
            composite_dev.setLayout(new GridLayout(1, false));
            
            TabItem tbtmNotes = new TabItem(tabFolder, SWT.NONE);
            tbtmNotes.setText("Developer Space");
            tbtmNotes.setControl(composite_dev);
            
            Text text_dev = new Text(composite_dev, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
            gridData = new GridData();
            gridData.grabExcessHorizontalSpace = true;
            gridData.horizontalAlignment       = GridData.FILL;
            gridData.grabExcessVerticalSpace   = true;
            gridData.verticalAlignment         = GridData.FILL;
            
            text_dev.setLayoutData(gridData);
            text_dev.setText("QEDIT DEBUG CONSOLE\n***************************************");
            qprint.configureText(text_dev);
        }
        
        shlQeditef.open();
        
        while (!shlQeditef.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
    }
}
