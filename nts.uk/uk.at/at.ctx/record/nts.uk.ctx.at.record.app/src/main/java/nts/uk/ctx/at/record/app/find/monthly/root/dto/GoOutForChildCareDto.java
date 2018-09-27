package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.GoOutForChildCare;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;

@Data
/** 育児外出 */
@NoArgsConstructor
@AllArgsConstructor
public class GoOutForChildCareDto implements ItemConst {

	/** 育児介護区分: 育児介護区分 */
//	@AttendanceItemValue(type = ValueType.INTEGER)
//	@AttendanceItemLayout(jpPropertyName = "育児介護区分", layout = "A", needCheckIDWithMethod = "childCareAtr")
	private int attr;
	
	/** 回数: 勤怠月間回数 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_B, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private int times;
	
	/** 時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_C, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private int time;
	
	public String enumText(){
		switch (this.attr) {
		case 0:
			return E_CHILD_CARE;
		case 1:
		default:
			return E_CARE;
		}
	}
	
	public static GoOutForChildCareDto from(GoOutForChildCare domain) {
		GoOutForChildCareDto dto = new GoOutForChildCareDto();
		if(domain != null) {
			dto.setAttr(domain.getChildCareAtr() == null ? 0 : domain.getChildCareAtr().value);
			dto.setTime(domain.getTime() == null ? 0 : domain.getTime().valueAsMinutes());
			dto.setTimes(domain.getTimes() == null ? 0 : domain.getTimes().v());
		}
		return dto;
	}
	
	public GoOutForChildCare toDomain(){
		return GoOutForChildCare.of(attr == ChildCareAtr.CARE.value ? ChildCareAtr.CARE : ChildCareAtr.CHILD_CARE, 
				new AttendanceTimesMonth(times), 
						new AttendanceTimeMonth(time));
	}
	
}
