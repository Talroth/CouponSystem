package DAOException;

import java.sql.SQLException;

public class DAOException extends SQLException 
{

	private static final long serialVersionUID = 1L;
	private DAOExceptionErrorType reason;

	public DAOException() {
		super();
	}

	public DAOException(String additionalMessage) {
		super(additionalMessage);
	}
	
	public DAOException(DAOExceptionErrorType reason) {
		super();
		this.reason = reason;
	}
	
	public DAOException(DAOExceptionErrorType reason, String additionalMessage) {
		super(additionalMessage);
		this.reason = reason;
	}
	
	public DAOExceptionErrorType getReason()
	{
		return this.reason;
	}
	
	public boolean isError(DAOExceptionErrorType reason)
	{
		return (this.reason == reason);
	}

	
}
