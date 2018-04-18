package nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 短時間勤務時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortWorkTimeSheetDto {

	/** 短時間勤務枠NO: 短時間勤務枠NO */
//	@AttendanceItemLayout(layout = "A", jpPropertyName = "")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer shortWorkTimeFrameNo;

	/** 育児介護区分: 育児介護区分 */
	/** @see nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute */
//	@AttendanceItemLayout(layout = "B", jpPropertyName = "")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer childCareAttr;

	/** 開始: 時刻(日区分付き) */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "開始", needCheckIDWithMethod = "childCare")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer startTime;

	/** 終了: 時刻(日区分付き) */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "終了", needCheckIDWithMethod = "childCare")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer endTime;

	/** 控除時間: 勤怠時間 */
//	@AttendanceItemLayout(layout = "E", jpPropertyName = "")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer deductionTime;

	/** 時間: 勤怠時間 */
//	@AttendanceItemLayout(layout = "F", jpPropertyName = "")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer shortTime;
	
	public String childCare(){
		if(childCareAttr == null){
			return "";
		}
		
		switch (childCareAttr) {
		case 0:
			return "育児";
		case 1:
			return "介護";
		default:
			return "";
		}
	}
}
