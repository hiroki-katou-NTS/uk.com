package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 日別実績の作業時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTimeOfDailyDto implements ItemConst {

	/** 作業枠NO: 作業枠NO */
	private int workFrameNo;

	/** 時間帯: 作業実績の時間帯 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = "")
	private ActualWorkTimeSheetDto timeSheet;

	/** 時間: 作業実績の時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = "")
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer workTime;
	
	@Override
	public WorkTimeOfDailyDto clone(){
		return new WorkTimeOfDailyDto(workFrameNo, 
									timeSheet == null ? null : timeSheet.clone(),
									workTime);
	}
}
