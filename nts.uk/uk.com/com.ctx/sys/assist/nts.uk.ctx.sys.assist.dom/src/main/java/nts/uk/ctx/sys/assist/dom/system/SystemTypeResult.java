package nts.uk.ctx.sys.assist.dom.system;

import lombok.Value;

@Value
public class SystemTypeResult {

	private final String systemTypeName;
	private final int systemTypeValue;
	
	public SystemTypeResult(String systemTypeName, int systemTypeValue) {
		super();
		this.systemTypeName = systemTypeName;
		this.systemTypeValue = systemTypeValue;
	}
	
	
}
