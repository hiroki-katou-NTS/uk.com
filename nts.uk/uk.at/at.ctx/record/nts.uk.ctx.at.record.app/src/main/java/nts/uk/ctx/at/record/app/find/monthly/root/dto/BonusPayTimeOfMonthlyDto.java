package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime.AggregateBonusPayTime;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の加給時間 + 集計加給時間 */
public class BonusPayTimeOfMonthlyDto {

	/** 加給枠NO: 加給時間項目NO */
	private int bonusPayFrameNo;

	/** 加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "加給時間", layout = "A")
	private int bonus;
	
	/** 休出加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "休出加給時間", layout = "B")
	private int holWorkBonus;
	
	/** 休出特定加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "休出特定加給時間", layout = "C")
	private int holWorkSpecBonus;
	
	/** 特定加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "特定加給時間", layout = "D")
	private int specBonus;
	
	public static BonusPayTimeOfMonthlyDto from(AggregateBonusPayTime domain) {
		BonusPayTimeOfMonthlyDto dto = new BonusPayTimeOfMonthlyDto();
		if(domain != null) {
			dto.setBonus(domain.getBonusPayTime() == null ? 0 : domain.getBonusPayTime().valueAsMinutes());
			dto.setBonusPayFrameNo(domain.getBonusPayFrameNo());
			dto.setHolWorkBonus(domain.getHolidayWorkBonusPayTime() == null ? 0 : domain.getHolidayWorkBonusPayTime().valueAsMinutes());
			dto.setHolWorkSpecBonus(domain.getHolidayWorkSpecificBonusPayTime() == null ? 0 : domain.getHolidayWorkSpecificBonusPayTime().valueAsMinutes());
			dto.setSpecBonus(domain.getSpecificBonusPayTime() == null ? 0 : domain.getSpecificBonusPayTime().valueAsMinutes());
		}
		return dto;
	}
	
	public AggregateBonusPayTime toDomain(){
		return AggregateBonusPayTime.of(bonusPayFrameNo, toAttendanceTimeMonth(bonus), toAttendanceTimeMonth(specBonus),
				toAttendanceTimeMonth(holWorkBonus), toAttendanceTimeMonth(holWorkSpecBonus));
	}
	
	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time){
		return new AttendanceTimeMonth(time);
	}
}
