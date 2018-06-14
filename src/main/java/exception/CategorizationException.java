package exception;

/**
 * Exception is thrown upon invalid definitions and wrapper for other checked Exceptions
 */
public class CategorizationException extends Exception {

    private static final String ERR_MESSAGE = "\nPlease check the log for more information. \n";
    private static String m_errorMsg ;

    public CategorizationException() {}

    public CategorizationException(String message) {
        super(message);
        setErrMessage(message);
    }

    public CategorizationException(Throwable cause) {
        super(cause);
        setErrMessage(cause.getMessage());
    }

    @Override
    public String getMessage() {
        return m_errorMsg;
    }

    public String getShortMessage() {
        return ERR_MESSAGE;
    }

    protected  void setErrMessage(String msg){
        m_errorMsg = msg + ERR_MESSAGE + "\n";
    }
}
