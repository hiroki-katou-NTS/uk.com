package nts.uk.ctx.sys.assist.dom.deletedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Result {
	private ResultState state;
	private Object data;
}
