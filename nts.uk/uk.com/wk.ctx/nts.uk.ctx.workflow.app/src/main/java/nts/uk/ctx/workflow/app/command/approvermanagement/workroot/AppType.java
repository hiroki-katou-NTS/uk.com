package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import lombok.Data;

@Data
public class AppType {
	private Integer value;
	private int employRootAtr;
	public AppType(Integer value, int employRootAtr) {
		super();
		this.value = value;
		this.employRootAtr = employRootAtr;
	}
	
}
