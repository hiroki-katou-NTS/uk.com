package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeBreakdown;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 36協定時間内訳 */
public class AgreementTimeBreakdownDto implements ItemConst{
	
	/** 残業時間 */
	@AttendanceItemLayout(jpPropertyName = OVERTIME, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.TIME)
	private int overTime;
	
	/** 振替残業時間 */
	@AttendanceItemLayout(jpPropertyName = OVERTIME + TRANSFER, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.TIME)
	private int transferOverTime;
	
	/** 休出時間 */
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_C)
	@AttendanceItemValue(type = ValueType.TIME)
	private int holidayWorkTime;
	
	/** 振替時間 */
	@AttendanceItemLayout(jpPropertyName = TRANSFER, layout = LAYOUT_D)
	@AttendanceItemValue(type = ValueType.TIME)
	private int transferTime;
	
	/** フレックス法定内時間 */
	@AttendanceItemLayout(jpPropertyName = FLEX + LEGAL, layout = LAYOUT_E)
	@AttendanceItemValue(type = ValueType.TIME)
	private int flexLegalTime;
	
	/** フレックス法定外時間 */
	@AttendanceItemLayout(jpPropertyName = FLEX + ILLEGAL, layout = LAYOUT_F)
	@AttendanceItemValue(type = ValueType.TIME)
	private int flexIllegalTime;
	
	/** 所定内割増時間 */
	@AttendanceItemLayout(jpPropertyName = WITHIN_STATUTORY + PREMIUM, layout = LAYOUT_G)
	@AttendanceItemValue(type = ValueType.TIME)
	private int withinPrescribedPremiumTime;
	
	/** 週割増時間 */
	@AttendanceItemLayout(jpPropertyName = WEEKLY_PREMIUM, layout = LAYOUT_H)
	@AttendanceItemValue(type = ValueType.TIME)
	private int weeklyPremiumTime;
	
	/** 月割増時間 */
	@AttendanceItemLayout(jpPropertyName = MONTHLY_PREMIUM, layout = LAYOUT_I)
	@AttendanceItemValue(type = ValueType.TIME)
	private int monthlyPremiumTime;
	
	public static AgreementTimeBreakdownDto from(AgreementTimeBreakdown domain) {
		if(domain != null){
			return new AgreementTimeBreakdownDto(domain.getOverTime().valueAsMinutes(), 
												domain.getTransferOverTime().valueAsMinutes(), 
												domain.getHolidayWorkTime().valueAsMinutes(), 
												domain.getTransferTime().valueAsMinutes(),
												domain.getFlexLegalTime().valueAsMinutes(), 
												domain.getFlexIllegalTime().valueAsMinutes(), 
												domain.getWithinPrescribedPremiumTime().valueAsMinutes(), 
												domain.getWeeklyPremiumTime().valueAsMinutes(), 
												domain.getMonthlyPremiumTime().valueAsMinutes());
		}
		return null;
	}
	
	public AgreementTimeBreakdown toDomain(){
		return AgreementTimeBreakdown.of(new AttendanceTimeMonth(overTime), 
											new AttendanceTimeMonth(transferOverTime), 
											new AttendanceTimeMonth(holidayWorkTime), 
											new AttendanceTimeMonth(transferTime), 
											new AttendanceTimeMonth(flexLegalTime), 
											new AttendanceTimeMonth(flexIllegalTime), 
											new AttendanceTimeMonth(withinPrescribedPremiumTime), 
											new AttendanceTimeMonth(weeklyPremiumTime), 
											new AttendanceTimeMonth(monthlyPremiumTime));
	}
}
