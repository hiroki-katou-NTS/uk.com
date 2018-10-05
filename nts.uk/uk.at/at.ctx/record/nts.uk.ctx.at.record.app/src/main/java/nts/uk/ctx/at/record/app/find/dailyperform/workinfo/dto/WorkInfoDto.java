package nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;

/** 勤務情報 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkInfoDto implements ItemConst {

	/** 勤務種類コード */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = WORK_TYPE)
	@AttendanceItemValue
	private String workTypeCode;

	/** 就業時間帯コード */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = WORK_TIME)
	@AttendanceItemValue
	private String workTimeCode;

	@Override
	protected WorkInfoDto clone() {
		return new WorkInfoDto(workTypeCode, workTimeCode);
	}
	
}
