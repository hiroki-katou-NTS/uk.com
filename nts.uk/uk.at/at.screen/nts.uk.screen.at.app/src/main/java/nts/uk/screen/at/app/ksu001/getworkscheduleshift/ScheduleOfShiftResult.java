/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;

/**
 * @author laitv
 *
 */
@Data
public class ScheduleOfShiftResult {


	public List<ScheduleOfShiftDto> listWorkScheduleShift;    // List<勤務予定（シフト）dto>
	public Map<ShiftMaster,Optional<WorkStyle>> shiftMasterMap; // Map<シフトマスタ, Optional<出勤休日区分>>
}
