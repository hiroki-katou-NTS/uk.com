package nts.uk.ctx.basic.app.command.organization.position;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UpdatePositionCommand {
	
	private String companyCode;
	private String historyID;
	private LocalDate startDate;
	private LocalDate endDate;
	private String jobCode;
	private String jobName;
	private String jobOutCode;
	private String memo;
	private String hiterarchyOrderCode;
	private int presenceCheckScopeSet;

}
