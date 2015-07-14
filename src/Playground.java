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

public class Playground
{
    private static Text txtTest;
    private static Text text;
    private static Shell shlQeditef;
    private static Display display;

    public static void main(String[] args)
    {
        System.out.println("qedit debug mode");
        display = Display.getDefault();
        shlQeditef = new Shell();
        shlQeditef.setSize(522, 363);
        shlQeditef.setText("Qedit");
        
        Composite composite = new Composite(shlQeditef, SWT.NONE);
        composite.setBounds(10, 10, 500, 318);
        
        TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
        tabFolder.setBounds(0, 0, 500, 308);
        
        TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
        tabItem.setText("New Item");
        
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
