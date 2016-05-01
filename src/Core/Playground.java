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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.graphics.Point;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

import Logging.QPrint;


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
        QPrint qprint = new QPrint("Main::open");
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(file, charset))
        {
            byte[] encoded = Files.readAllBytes(file);
            String toPrint = new String(encoded, charset);
            qprint.verbose("toPrint="+toPrint);
            if ( null == text )
            {
                
                return;
            }
            text.setText(toPrint);
        }
        catch (IOException x)
        {
            qprint.error("Exception: " + x.getMessage());
        }
    }

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
        Button buttonOpen = new Button(composite_3, SWT.NONE);
        final QFileManager fileManager = new QFileManager(ROOT);
        buttonOpen.setText("Open");
        buttonOpen.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event)
            {
                fileManager.open();
            }
        });
        gridData = new GridData();
        buttonOpen.setLayoutData(gridData);
        Button buttonAdd = new Button(composite_3, SWT.NONE);
        buttonAdd.setText("Add");
        buttonAdd.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event)
            {
                fileManager.add();
            }
        });
        gridData = new GridData();
        buttonAdd.setLayoutData(gridData);
        Button buttonDelete = new Button(composite_3, SWT.NONE);
        buttonDelete.setText("Delete");
        buttonDelete.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event)
            {
                fileManager.del();
            }
        });
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
                    fileManager.save();
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
            public void mouseDown(MouseEvent e)
            {
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
                open(path, text_1);
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
        
        /////////////
        // BUTTONS
        /////////////
        Button btnNewButton = new Button(composite_1, SWT.NONE);
        btnNewButton.setText("Add Machine");
        btnNewButton.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent event)
            {
                // add a new SSH configuration by adding a new machine in list
                qssh.add();
            }
        });
        gridData = new GridData();
        btnNewButton.setLayoutData(gridData);
        
        /////////////
        // TEXT
        /////////////
        txtTest = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
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
        Tree tree = new Tree(composite_1, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
        
        TreeItem trtmMachine = new TreeItem(tree, SWT.NONE);
        trtmMachine.setGrayed(true);
        trtmMachine.setText("machine1");
        
        gridData = new GridData();
        gridData.verticalAlignment         = GridData.FILL;
        gridData.horizontalAlignment       = GridData.FILL;
        gridData.grabExcessVerticalSpace   = true;
        gridData.grabExcessHorizontalSpace = false;
        tree.setLayoutData(gridData);
        
        //////////////////////////////////////////////////////////////////////////////////
        // ITEM 3
        //////////////////////////////////////////////////////////////////////////////////
        
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
