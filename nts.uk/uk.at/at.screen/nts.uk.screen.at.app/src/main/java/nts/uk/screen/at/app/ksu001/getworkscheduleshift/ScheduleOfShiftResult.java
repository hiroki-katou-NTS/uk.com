/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;

/**
 * @author laitv
 *
 */
@Data
@AllArgsConstructor
public class ScheduleOfShiftResult {

	public List<ScheduleOfShiftDto> listWorkScheduleShift;    // List<勤務予定（シフト）dto>
	public List<ShiftMasterMapWithWorkStyle> shiftMasterWithWorkStyleLst; // Map<シフトマスタ, Optional<出勤休日区分>>
	
}
