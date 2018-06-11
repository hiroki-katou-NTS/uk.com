package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.GoOutForChildCare;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;

@Data
/** 育児外出 */
@NoArgsConstructor
@AllArgsConstructor
public class GoOutForChildCareDto {

	/** 育児介護区分: 育児介護区分 */
//	@AttendanceItemValue(type = ValueType.INTEGER)
//	@AttendanceItemLayout(jpPropertyName = "育児介護区分", layout = "A", needCheckIDWithMethod = "childCareAtr")
	private int childCareAtr;
	
	/** 回数: 勤怠月間回数 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "回数", layout = "B", needCheckIDWithMethod = "childCareAtr")
	private int times;
	
	/** 時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "時間", layout = "C", needCheckIDWithMethod = "childCareAtr")
	private int time;
	
	public String childCareAtr(){
		switch (this.childCareAtr) {
		case 0:
			return "育児";
		case 1:
		default:
			return "介護";
		}
	}
	
	public static GoOutForChildCareDto from(GoOutForChildCare domain) {
		GoOutForChildCareDto dto = new GoOutForChildCareDto();
		if(domain != null) {
			dto.setChildCareAtr(domain.getChildCareAtr() == null ? 0 : domain.getChildCareAtr().value);
			dto.setTime(domain.getTime() == null ? 0 : domain.getTime().valueAsMinutes());
			dto.setTimes(domain.getTimes() == null ? 0 : domain.getTimes().v());
		}
		return dto;
	}
	
	public GoOutForChildCare toDomain(){
		return GoOutForChildCare.of(ConvertHelper.getEnum(childCareAtr, ChildCareAtr.class), 
				new AttendanceTimesMonth(times), 
						new AttendanceTimeMonth(time));
	}
	
}
