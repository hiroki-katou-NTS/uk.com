package nts.uk.screen.at.ws.schedule.employeeinfo;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.ksu001.sortsetting.EmployeeInfoKsuSDto;
import nts.uk.screen.at.app.ksu001.sortsetting.EmployeeSwapDto;
@Value
public class CommandKSU001S {
	private List<EmployeeInfoKsuSDto> listEmpId;
	private String date;
	private List<EmployeeSwapDto> selectedEmployeeSwap;
}