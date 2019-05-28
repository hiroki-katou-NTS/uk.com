package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto.Career;

@Data
public class CareerPartHistoryCommand {

	private String historyId;
	
	private String startDate;
	
	private List<Career> career;
}
