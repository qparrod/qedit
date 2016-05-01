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
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

// manuel
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.graphics.Point;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.FileSystem;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class Playground
{
    private static Text txtTest;
    private static Text text;
    private static Shell shlQeditef;
    private static Display display;
    private static Text text_1;
    public static String ROOT;
    private static Tree tree_2;
    
    private static void open (Path file, Text text)
    {
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(file, charset))
        {
            byte[] encoded = Files.readAllBytes(file);
            String toPrint = new String(encoded, charset);
            System.out.println("toPrint="+toPrint);
            if ( null == text )
            {
                return;
            }
            text.setText(toPrint);
        }
        catch (IOException x)
        {
            System.err.format("IOException: %s%n.", x);
        }
    }

    public static void main(String[] args)
    {
        System.out.println("qedit debug mode");
        display = Display.getDefault();
        shlQeditef = new Shell();
        shlQeditef.setLayout(new GridLayout(1, false));
        shlQeditef.setText("Qedit");
        
        Composite composite = new Composite(shlQeditef, SWT.NONE);
        composite.setLayout(new GridLayout(1, true));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
        tabFolder.setLayout(new GridLayout(2, true));
        tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
        tabItem.setText("New Item");
        
        Composite composite_3 = new Composite(tabFolder, SWT.NONE);
        composite_3.setLayout(new GridLayout(2, true));
        composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        tabItem.setControl(composite_3);
        new Label(composite_3, SWT.NONE);
        new Label(composite_3, SWT.NONE);
        
        text_1 = new Text( composite_3, SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        text_1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if ( (e.stateMask & SWT.CTRL) != 0 && (e.keyCode == 115)) 
                {
                    System.out.println("Save File"); 
                }
            }
        });
        text_1.setBounds(161, 10, 321, 260);
        
        tree_2 = new Tree(composite_3, SWT.BORDER);
        // open recursively files already present in software repository
        QTree _tree = new QTree();
        _tree.setComposite(composite_3);
        _tree.open(tree_2);
        
        tree_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                int buttonType = e.button;
                switch(buttonType)
                {
                    case 1:
                        //System.out.println("left button clicked");
                        break;
                    case 3:
                        //System.out.println("right button clicked");
                        // swing lib to open window
                        break;
                }
            }
            
            public String findAbsolutePath(TreeItem tree, String path)
            {
                if ( System.getProperty("os.name").startsWith("Linux") )
                {
                    ROOT = "/home/qparrod/workspace/qedit/store/";
                }
                else if ( System.getProperty("os.name").startsWith("Windows") )
                {
                    ROOT = "G:/repo_qedit/store/";
                }
                
                String str = ROOT + path;
                
                if(null == tree.getParentItem())
                {
                    return str;
                }
                String parent = tree.getParentItem().getText();
                return findAbsolutePath(tree.getParentItem(), parent + "/"+ path);
            }
            
            public void mouseDoubleClick(MouseEvent event)
            {
                if ( tree_2 == null )
                {
                    System.out.println("tree_2 is null");
                    return;
                }
                TreeItem dest = tree_2.getItem(new Point(event.x, event.y));
                if (dest != null)
                {
                    Path path = Paths.get(findAbsolutePath(dest, dest.getText()));
                    System.out.println("open file "+ path.toString());
                    open(path, text_1);
                }
            }
        });
        tree_2.setTouchEnabled(true);
        tree_2.setBounds(10, 10, 145, 260);
        
        GridData gridData = new GridData( GridData.HORIZONTAL_ALIGN_FILL | 
                                          GridData.VERTICAL_ALIGN_FILL);
        gridData.grabExcessVerticalSpace = true;
        
        text_1.setLayoutData(gridData);
        _tree.setOutput(text_1);
        
        TabItem tbtmMachines = new TabItem(tabFolder, SWT.NONE);
        tbtmMachines.setText("Machines");
        
        Composite composite_1 = new Composite(tabFolder, SWT.NONE);
        tbtmMachines.setControl(composite_1);
        composite_1.setLayout(new GridLayout(2, false));
        
        Button btnNewButton = new Button(composite_1, SWT.NONE);
        btnNewButton.setText("Add Machine");
        new Label(composite_1, SWT.NONE);
        
        Tree tree = new Tree(composite_1, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
        tree.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                System.out.println("OK1");
            }
        });
        tree.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
        tree.setToolTipText("test");
        
        TreeItem trtmMachine = new TreeItem(tree, SWT.NONE);
        trtmMachine.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type)
                {
                    case SWT.Selection:
                        System.out.println("OK2");
                        break;
                }
                System.out.println("KO");
            }
        });
        trtmMachine.setGrayed(true);
        trtmMachine.setText("machine2");
        
        txtTest = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
        GridData gd_txtTest = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_txtTest.heightHint = 138;
        txtTest.setLayoutData(gd_txtTest);
        txtTest.setText("test cette fenetre doit servir de console pour le debug\nmais je ne sais pas si ca va marcher");
        
        
        
        TabItem tbtmNotes = new TabItem(tabFolder, SWT.NONE);
        tbtmNotes.setText("Notes");
        
        Composite composite_2 = new Composite(tabFolder, SWT.NONE);
        tbtmNotes.setControl(composite_2);
        composite_2.setLayout(new FormLayout());
        
        text = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
        FormData fd_text = new FormData();
        fd_text.right = new FormAttachment(100, -52);
        fd_text.top = new FormAttachment(0, 35);
        fd_text.left = new FormAttachment(100, -269);
        text.setLayoutData(fd_text);
        
        Label lblNewLabel = new Label(composite_2, SWT.NONE);
        FormData fd_lblNewLabel = new FormData();
        fd_lblNewLabel.bottom = new FormAttachment(text, -8);
        lblNewLabel.setLayoutData(fd_lblNewLabel);
        lblNewLabel.setText("debug console");
        
        Tree tree_1 = new Tree(composite_2, SWT.BORDER);
        fd_text.bottom = new FormAttachment(tree_1, 0, SWT.BOTTOM);
        FormData fd_tree_1 = new FormData();
        fd_tree_1.left = new FormAttachment(0);
        tree_1.setLayoutData(fd_tree_1);
        
        Button btnNewButton_1 = new Button(composite_2, SWT.NONE);
        btnNewButton_1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                text.insert("Add key selected\n");
                // add a new item each time add
                // launch a new window with settings

                //AddSss addsss = new AddSss(shlQeditef, 0);
                //addsss.open();
                
                AddSshConsole ssh = new AddSshConsole(display);
                ssh.open();
                /*
                while (!ssh.isDisposed())
                {
                    if (ssh.exit)
                    {
                        ssh.close();
                    }
                }*/
            }
        });
        fd_tree_1.bottom = new FormAttachment(btnNewButton_1, 217, SWT.BOTTOM);
        fd_tree_1.top = new FormAttachment(btnNewButton_1, 8);
        btnNewButton_1.setLayoutData(new FormData());
        btnNewButton_1.setText("Add");
        
        Button btnNewButton_2 = new Button(composite_2, SWT.NONE);
        fd_lblNewLabel.left = new FormAttachment(btnNewButton_2, 144);
        fd_tree_1.right = new FormAttachment(btnNewButton_2, 77, SWT.RIGHT);
        FormData fd_btnNewButton_2 = new FormData();
        fd_btnNewButton_2.top = new FormAttachment(btnNewButton_1, 0, SWT.TOP);
        fd_btnNewButton_2.left = new FormAttachment(btnNewButton_1, 6);
        btnNewButton_2.setLayoutData(fd_btnNewButton_2);
        btnNewButton_2.setText("Copy");
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
