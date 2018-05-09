package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime.AggregatePremiumTime;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
/** 集計割増時間 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregatePremiumTimeDto {

	/** 割増時間項目NO: 割増時間項目NO */
	private int premiumTimeItemNo;

	/** 時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "時間", layout = "A")
	private Integer time;
	
	public static AggregatePremiumTimeDto from (AggregatePremiumTime domain) {
		AggregatePremiumTimeDto dto = new AggregatePremiumTimeDto();
		if(domain != null) {
			dto.setPremiumTimeItemNo(domain.getPremiumTimeItemNo());
			dto.setTime(domain.getTime() == null ? null : domain.getTime().valueAsMinutes());
		}
		return dto;
	}

	public AggregatePremiumTime toDomain(){
		return AggregatePremiumTime.of(premiumTimeItemNo, time == null ? null : new AttendanceTimeMonth(time));
	}
}
