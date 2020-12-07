/**
 * 
 */
package nts.uk.screen.at.app.ksu001.processcommon;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftDto;

/**
 * @author laitv
 *
 */
@Data
@AllArgsConstructor
public class WorkScheduleShiftBaseResult {

	public List<ScheduleOfShiftDto> listWorkScheduleShift;    // List<勤務予定（シフト）dto>
	public Map<ShiftMaster,Optional<WorkStyle>> mapShiftMasterWithWorkStyle; // Map<シフトマスタ, Optional<出勤休日区分>>
	
	
}
