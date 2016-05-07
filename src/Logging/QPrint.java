package Logging;

import java.lang.String;

import org.eclipse.swt.widgets.Text;

public final class QPrint
{
    private int     level;
    private String  moduleName;
    private static Text    text;
    private static boolean enableDbgTxt = false;
    
    public QPrint(String module)
    {
        setModuleName(module);
        setLevel(QLevel.VERBOSE); // default level
    }
    
    public QPrint (String module, int level)
    {
        setModuleName(module);
        setLevel(level);
    }
    
    private void qprint(String trace, int level, String arg)
    {
        if ( this.level >= level )
        {
            System.out.println(trace + moduleName + ": " + arg);
            
            if (enableDbgTxt)
            {
                if (text != null )
                {
                    text.append("\n");
                    text.append(trace + moduleName + ": " + arg);
                }
            }
        }
    }
    
    public void error(String arg)
    {
        qprint(QTrace.ERROR, QLevel.ERROR, arg);
    }
    
    public void warning(String arg)
    {
        qprint(QTrace.WARNING, QLevel.WARNING, arg);
    }
    
    public void verbose(String arg)
    {
        qprint(QTrace.VERBOSE, QLevel.VERBOSE, arg);
    }
    
    public void setLevel (int level)
    {
        this.level = level;
    }
    
    public void setModuleName(String name)
    {
        this.moduleName = name;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public String getModuleName()
    {
        return moduleName;
    }
    
    public void configureText(Text text)
    {
        qprint(QTrace.VERBOSE, QLevel.VERBOSE, "enable DBG text");
        enableDbgTxt = true;
        this.text = text;
    }
}

class QLevel
{
    public static final int NO_LOGGING = 0;
    public static final int ERROR      = 1;
    public static final int WARNING    = 2;
    public static final int VERBOSE    = 3;
}

class QTrace
{
    public static final String ERROR   = "[ERROR] "  ;
    public static final String WARNING = "[WARNING] ";
    public static final String VERBOSE = "[DBG] "    ;
}
