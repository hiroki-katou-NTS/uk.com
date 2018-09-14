package nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 短時間勤務時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortWorkTimeSheetDto implements ItemConst {

	/** 短時間勤務枠NO: 短時間勤務枠NO */
	// @AttendanceItemLayout(layout = "A", jpPropertyName = "")
	// @AttendanceItemValue(type = ValueType.INTEGER)
	private Integer no;

	/** 育児介護区分: 育児介護区分 */
	/** @see nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute */
	// @AttendanceItemLayout(layout = "B", jpPropertyName = "")
	// @AttendanceItemValue(type = ValueType.INTEGER)
	private int attr;

	/** 開始: 時刻(日区分付き) */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = START, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer startTime;

	/** 終了: 時刻(日区分付き) */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = END, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer endTime;

	/** 控除時間: 勤怠時間 */
	// @AttendanceItemLayout(layout = "E", jpPropertyName = "")
	// @AttendanceItemValue(type = ValueType.INTEGER)
	private Integer deductionTime;

	/** 時間: 勤怠時間 */
	// @AttendanceItemLayout(layout = "F", jpPropertyName = "")
	// @AttendanceItemValue(type = ValueType.INTEGER)
	private Integer shortTime;

	@Override
	public ShortWorkTimeSheetDto clone(){
		return new ShortWorkTimeSheetDto(no, attr, startTime, endTime, deductionTime, shortTime);
	}
	
	public String enumText() {
		switch (attr) {
		case 0:
			return E_CHILD_CARE;
		case 1:
			return E_CARE;
		default:
			return EMPTY_STRING;
		}
	}
}
