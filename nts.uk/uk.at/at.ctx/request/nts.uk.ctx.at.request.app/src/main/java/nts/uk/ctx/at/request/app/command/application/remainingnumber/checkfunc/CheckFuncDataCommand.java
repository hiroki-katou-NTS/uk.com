package nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class CheckFuncDataCommand {
	private int total;
	private int error;
	private int pass;
	private List<OutputErrorInfoCommand> outputErrorList;
	private List<EmployeeSearchCommand> employeeList;
	private GeneralDate startTime;
	private GeneralDate endTime;
	private GeneralDate date;
	private Double maxDay;
}
