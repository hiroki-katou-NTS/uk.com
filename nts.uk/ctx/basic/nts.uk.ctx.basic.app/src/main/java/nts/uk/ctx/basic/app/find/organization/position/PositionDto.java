package nts.uk.ctx.basic.app.find.organization.position;

import java.time.LocalDate;

import lombok.Data;

@Data

public class PositionDto {
	private String historyID;
	private String jobCode;
	private String jobName;
	private String jobOutCode;
	private LocalDate startDate;
	private LocalDate endDate;
	private String memo;
	
}
