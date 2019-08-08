package nts.uk.shr.pereg.app.command;

import java.util.ArrayList;
import java.util.List;

import nts.arc.error.BusinessException;

public class MyCustomizeException extends BusinessException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private List<String> errorLst = new ArrayList<>();

	public MyCustomizeException(String messageId, List<String> errorLst) {
		super(messageId);
		this.errorLst.addAll(errorLst);
	}

}
