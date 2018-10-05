package nts.uk.ctx.at.function.app.command.alarm.alarmlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
@Data
@AllArgsConstructor
public class ErrorAlarmListCommand {
	private List<EmployeeSearchDto> listEmployee;
	private String alarmCode;
	private List<PeriodByAlarmCategory> listPeriodByCategory;
	private String statusProcessId;
}
