package nts.uk.ctx.sys.assist.dom.deletedata;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteDataException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String sid;

	public DeleteDataException(String message, String sid) {
		super(message);
		this.sid = sid.substring(0, sid.indexOf(',')).replace("'", "");
	}
}
