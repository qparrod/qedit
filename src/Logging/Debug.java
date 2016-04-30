package Logging;

import java.lang.String;

public final class Debug
{
    //private int level;
    
    public static final int NO_LOGGING = 0;
    public static final int ERROR      = 1;
    public static final int WARNING    = 2;
    public static final int VERBOSE    = 3;
    
    
    private Debug(int level)
    {
        //this.level = level;
        System.out.println("opening debug");
    }
    
    public static void verbose(String arg)
    {
        System.out.println(arg);
    }
    
    public static void error()
    {
        
    }
    
    public static void warning()
    {
        
    }
}
