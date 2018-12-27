package nts.uk.ctx.at.record.app.command.dailyperform.audittrail;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.dom.daily.itemvalue.DailyItemValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyCorrectionLogCommand {
	
	private List<DailyItemValue> dailyOld;
	
	private List<DailyItemValue> dailyNew;
	
	List<DailyRecordWorkCommand> commandNew;
	
	Map<Integer, DPAttendanceItemRC> lstAttendanceItem;

}
