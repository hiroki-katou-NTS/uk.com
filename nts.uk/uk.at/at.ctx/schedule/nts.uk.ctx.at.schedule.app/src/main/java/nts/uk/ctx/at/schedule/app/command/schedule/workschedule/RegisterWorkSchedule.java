/**
 * 
 */
package nts.uk.ctx.at.schedule.app.command.schedule.workschedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;

/**
 * @author laitv
 */

@Stateless
public class RegisterWorkSchedule {

	private static final String DATE_FORMAT = "yyyy/MM/dd";

	public void handle(List<WorkScheduleCommand> command) {
		if (command.isEmpty())
			return;
		if (command.get(0).viewMode == "shift") {
			List<WorkScheduleSaveCommand> dataReg = convertParam(command, "shift");
			System.out.println(dataReg);
		} else {
			List<WorkScheduleSaveCommand> dataReg = convertParam(command, "other");
			System.out.println(dataReg);
		}
		
	}

	private List<WorkScheduleSaveCommand> convertParam(List<WorkScheduleCommand> command, String viewMode) {
		List<WorkScheduleSaveCommand> rs = new ArrayList<WorkScheduleSaveCommand>();
		if (viewMode == "shift") {
			for (WorkScheduleCommand wsCmd : command) {
				GeneralDate ymd = GeneralDate.fromString(wsCmd.ymd, DATE_FORMAT);
				WorkScheduleSaveCommand ws = new WorkScheduleSaveCommand(wsCmd.sid, ymd, wsCmd.shiftCode);
				rs.add(ws);
			}
		} else {
			for (WorkScheduleCommand wsCmd : command) {
				GeneralDate ymd = GeneralDate.fromString(wsCmd.ymd, DATE_FORMAT);
				String workTypeCd = wsCmd.workTypeCd;
				String workTimeCd = wsCmd.workTimeCd;
				WorkInformationDto workInfor = new WorkInformationDto(workTypeCd, workTimeCd);
				Map<Integer, Integer> mapAttendIdWithTime = new HashMap<Integer, Integer>();
				Integer startTime = wsCmd.startTime;
				Integer endTime   = wsCmd.endTime;
				if (wsCmd.isChangeTime) {
					mapAttendIdWithTime.put(31, startTime);
					mapAttendIdWithTime.put(34, endTime);
				}
				WorkScheduleSaveCommand ws = new WorkScheduleSaveCommand(wsCmd.sid, ymd, workInfor, mapAttendIdWithTime, new ArrayList<>(), null);
				rs.add(ws);
			}
		}
		return rs;
	}
}
