package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeBreakdown;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 36協定時間内訳 */
public class AgreementTimeBreakdownDto implements ItemConst, AttendanceItemDataGate {
	
	/** 残業時間 */
	@AttendanceItemLayout(jpPropertyName = OVERTIME, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.TIME)
	private int overTime;
	
	/** 振替残業時間 */
	@AttendanceItemLayout(jpPropertyName = OVERTIME + TRANSFER, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.TIME)
	private int transferOverTime;
	
	/** 法定内休出時間 */
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK + LEGAL, layout = LAYOUT_J)
	@AttendanceItemValue(type = ValueType.TIME)
	private int legalHolidayWorkTime;
	
	/** 法定内振替時間 */
	@AttendanceItemLayout(jpPropertyName = TRANSFER + LEGAL, layout = LAYOUT_K)
	@AttendanceItemValue(type = ValueType.TIME)
	private int legalTransferTime;
	
	/** 法定外休出時間 */
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_C)
	@AttendanceItemValue(type = ValueType.TIME)
	private int holidayWorkTime;
	
	/** 法定外振替時間 */
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
	
	/** 臨時時間 */
	@AttendanceItemLayout(jpPropertyName = TEMPORARY, layout = LAYOUT_I)
	@AttendanceItemValue(type = ValueType.TIME)
	private int temporaryTime;
	
	public static AgreementTimeBreakdownDto from(AgreementTimeBreakdown domain) {
		if(domain != null){
			return new AgreementTimeBreakdownDto(domain.getOverTime().valueAsMinutes(), 
												domain.getTransferOverTime().valueAsMinutes(), 
												domain.getLegalHolidayWorkTime().valueAsMinutes(), 
												domain.getLegalTransferTime().valueAsMinutes(),
												domain.getIllegalHolidayWorkTime().valueAsMinutes(),
												domain.getIllegaltransferTime().valueAsMinutes(),
												domain.getFlexLegalTime().valueAsMinutes(), 
												domain.getFlexIllegalTime().valueAsMinutes(), 
												domain.getWithinPrescribedPremiumTime().valueAsMinutes(), 
												domain.getWeeklyPremiumTime().valueAsMinutes(), 
												domain.getMonthlyPremiumTime().valueAsMinutes(),
												domain.getTemporaryTime().valueAsMinutes());
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
											new AttendanceTimeMonth(monthlyPremiumTime),
											new AttendanceTimeMonth(temporaryTime),
											new AttendanceTimeMonth(legalHolidayWorkTime),
											new AttendanceTimeMonth(legalTransferTime));
	}
	

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case OVERTIME:
			return Optional.of(ItemValue.builder().value(overTime).valueType(ValueType.TIME));
		case (OVERTIME + TRANSFER):
			return Optional.of(ItemValue.builder().value(transferOverTime).valueType(ValueType.TIME));
		case HOLIDAY_WORK:
			return Optional.of(ItemValue.builder().value(holidayWorkTime).valueType(ValueType.TIME));
		case TRANSFER:
			return Optional.of(ItemValue.builder().value(transferTime).valueType(ValueType.TIME));
		case (FLEX + LEGAL):
			return Optional.of(ItemValue.builder().value(flexLegalTime).valueType(ValueType.TIME));
		case (FLEX + ILLEGAL):
			return Optional.of(ItemValue.builder().value(flexIllegalTime).valueType(ValueType.TIME));
		case (WITHIN_STATUTORY + PREMIUM):
			return Optional.of(ItemValue.builder().value(withinPrescribedPremiumTime).valueType(ValueType.TIME));
		case WEEKLY_PREMIUM:
			return Optional.of(ItemValue.builder().value(weeklyPremiumTime).valueType(ValueType.TIME));
		case MONTHLY_PREMIUM:
			return Optional.of(ItemValue.builder().value(monthlyPremiumTime).valueType(ValueType.TIME));
//		case (HOLIDAY_WORK + LEGAL):
//			return Optional.of(ItemValue.builder().value(legalHolidayWorkTime).valueType(ValueType.TIME));
//		case (TRANSFER + LEGAL):
//			return Optional.of(ItemValue.builder().value(legalTransferTime).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case OVERTIME:
		case (OVERTIME + TRANSFER):
		case HOLIDAY_WORK:
		case TRANSFER:
		case (FLEX + LEGAL):
		case (FLEX + ILLEGAL):
		case (WITHIN_STATUTORY + PREMIUM):
		case WEEKLY_PREMIUM:
		case MONTHLY_PREMIUM:
//		case (HOLIDAY_WORK + LEGAL):
//		case (TRANSFER + LEGAL):
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case OVERTIME:
			overTime = value.valueOrDefault(0); break;
		case (OVERTIME + TRANSFER):
			transferOverTime = value.valueOrDefault(0); break;
		case HOLIDAY_WORK:
			holidayWorkTime = value.valueOrDefault(0); break;
		case TRANSFER:
			transferTime = value.valueOrDefault(0); break;
		case (FLEX + LEGAL):
			flexLegalTime = value.valueOrDefault(0); break;
		case (FLEX + ILLEGAL):
			flexIllegalTime = value.valueOrDefault(0); break;
		case (WITHIN_STATUTORY + PREMIUM):
			withinPrescribedPremiumTime = value.valueOrDefault(0); break;
		case WEEKLY_PREMIUM:
			weeklyPremiumTime = value.valueOrDefault(0); break;
		case MONTHLY_PREMIUM:
			monthlyPremiumTime = value.valueOrDefault(0); break;
//		case (HOLIDAY_WORK + LEGAL):
//			legalHolidayWorkTime = value.valueOrDefault(0); break;
//		case (TRANSFER + LEGAL):
//			legalTransferTime = value.valueOrDefault(0); break;
		default:
			break;
		}
	}
}
