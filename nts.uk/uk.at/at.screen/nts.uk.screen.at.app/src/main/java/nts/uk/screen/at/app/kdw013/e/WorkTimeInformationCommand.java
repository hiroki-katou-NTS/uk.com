package nts.uk.screen.at.app.kdw013.e;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author tutt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTimeInformationCommand {
	
	// 時刻
	public Integer timeWithDay;
	
	public static WorkTimeInformation toDomain(WorkTimeInformationCommand command) {
		return new WorkTimeInformation(null, new TimeWithDayAttr(command.timeWithDay));
	}
}
