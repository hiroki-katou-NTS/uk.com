package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime.AggregateBonusPayTime;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の加給時間 + 集計加給時間 */
public class BonusPayTimeOfMonthlyDto implements ItemConst {

	/** 加給枠NO: 加給時間項目NO */
	private int no;

	/** 加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	private int bonus;
	
	/** 休出加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_B)
	private int holWorkBonus;
	
	/** 休出特定加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK + SPECIFIC, layout = LAYOUT_C)
	private int holWorkSpecBonus;
	
	/** 特定加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = SPECIFIC, layout = LAYOUT_D)
	private int specBonus;
	
	public static BonusPayTimeOfMonthlyDto from(AggregateBonusPayTime domain) {
		BonusPayTimeOfMonthlyDto dto = new BonusPayTimeOfMonthlyDto();
		if(domain != null) {
			dto.setBonus(domain.getBonusPayTime() == null ? 0 : domain.getBonusPayTime().valueAsMinutes());
			dto.setNo(domain.getBonusPayFrameNo());
			dto.setHolWorkBonus(domain.getHolidayWorkBonusPayTime() == null ? 0 : domain.getHolidayWorkBonusPayTime().valueAsMinutes());
			dto.setHolWorkSpecBonus(domain.getHolidayWorkSpecificBonusPayTime() == null ? 0 : domain.getHolidayWorkSpecificBonusPayTime().valueAsMinutes());
			dto.setSpecBonus(domain.getSpecificBonusPayTime() == null ? 0 : domain.getSpecificBonusPayTime().valueAsMinutes());
		}
		return dto;
	}
	
	public AggregateBonusPayTime toDomain(){
		return AggregateBonusPayTime.of(no, toAttendanceTimeMonth(bonus), toAttendanceTimeMonth(specBonus),
				toAttendanceTimeMonth(holWorkBonus), toAttendanceTimeMonth(holWorkSpecBonus));
	}
	
	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time){
		return new AttendanceTimeMonth(time);
	}
}
