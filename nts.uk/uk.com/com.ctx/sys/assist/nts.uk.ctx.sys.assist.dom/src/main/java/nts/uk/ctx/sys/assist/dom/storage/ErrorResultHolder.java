package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResultHolder {
	private String sid;
	private Exception exception;
	private ResultState resultState;
	
	public ErrorResultHolder(ResultState resultState) {
		super();
		this.resultState = resultState;
	}
}
