package nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

/** 短時間勤務時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortWorkTimeSheetDto implements ItemConst, AttendanceItemDataGate {

	/** 短時間勤務枠NO: 短時間勤務枠NO */
	// @AttendanceItemLayout(layout = "A", jpPropertyName = "")
	// @AttendanceItemValue(type = ValueType.INTEGER)
	private int no;

	/** 育児介護区分: 育児介護区分 */
	/** @see nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute */
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
	
	@Override
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

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case START:
			return Optional.of(ItemValue.builder().value(startTime).valueType(ValueType.TIME_WITH_DAY));
		case END:
			return Optional.of(ItemValue.builder().value(endTime).valueType(ValueType.TIME_WITH_DAY));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case START:
			this.startTime = value.valueOrDefault(null);
			break;
		case END:
			this.endTime = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case START:
		case END:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void setEnum(String enumText) {
		switch (enumText) {
		case E_CHILD_CARE:
			this.attr = 0;
			break;
		case E_CARE:
			this.attr = 1;
			break;
		default:
		}
	}

//	@Override
//	public boolean enumNeedSet() {
//		return true;
//	}
	
	
}
