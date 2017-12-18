package nts.uk.ctx.at.record.app.find.actualworkinghours.workinfo.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;

/** 勤務情報 */
@Data
public class WorkInfoDto {

	/** 勤務種類コード */
	@AttendanceItemLayout(layout = "A")
	@AttendanceItemValue(itemId = { 28, 1 }, idReferenceMethod = "getIdIdx", isReferenceParentLayout = true)
	private String workTypeCode;

	/** 就業時間帯コード */
	@AttendanceItemLayout(layout = "B")
	@AttendanceItemValue(itemId = { 29, 2 }, idReferenceMethod = "getIdIdx", isReferenceParentLayout = true)
	private String workTimeCode;

	public int getIdIdx(String parentLayout) {
		return parentLayout.equals("A") ? 0 : 1;
	}
}
