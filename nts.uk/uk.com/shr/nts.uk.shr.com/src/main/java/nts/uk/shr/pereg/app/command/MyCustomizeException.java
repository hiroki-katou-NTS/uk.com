package nts.uk.shr.pereg.app.command;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
@Getter
@Setter
public class MyCustomizeException extends BusinessException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private List<String> errorLst = new ArrayList<>();
	
	private String itemName;

	public MyCustomizeException(String messageId, List<String> errorLst) {
		super(messageId);
		this.errorLst.addAll(errorLst);
	}
	
	public MyCustomizeException(String messageId, List<String> errorLst, String itemName) {
		super(messageId);
		this.errorLst.addAll(errorLst);
		this.itemName = itemName;
	}

}
