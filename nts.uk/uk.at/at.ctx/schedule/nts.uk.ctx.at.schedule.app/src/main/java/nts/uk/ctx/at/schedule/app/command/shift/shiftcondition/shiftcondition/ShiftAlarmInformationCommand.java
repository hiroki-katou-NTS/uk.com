package nts.uk.ctx.at.schedule.app.command.shift.shiftcondition.shiftcondition;

import java.util.List;
import java.util.Map;

import lombok.Value;

@Value
public class ShiftAlarmInformationCommand {
	/**
	 *  対象者 and EmployeeName
	 */
	private List<EmployeeCommand> employee;
	/**
	 * 選択条件
	 */
	private List<Integer> conditionNos;
	/**
	 * 終了時刻
	 */
	private Integer endTime;
	/**
	 * 開始時刻
	 */
	private Integer startTime;

}
