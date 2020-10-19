package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の36協定時間 */
public class AgreementTimeOfMonthlyDto implements ItemConst {

	/** 36協定時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = AGREEMENT, layout = LAYOUT_A)
	private int agreementTime;

	/** エラー時間 : ３６協定１ヶ月時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LIMIT_ALARM, layout = LAYOUT_B)
	private int alarmTime;

	/** アラーム時間: ３６協定１ヶ月時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = LIMIT_ERROR, layout = LAYOUT_C)
	private int errorTime;

	/** 上限時間: ３６協定１ヶ月時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = UPPER_LIMIT, layout = LAYOUT_E)
	private Integer exceptionLimitTime;

	public AgreementTimeOfMonthly toDomain() {
		return AgreementTimeOfMonthly.of(new AttendanceTimeMonth(agreementTime),
										OneMonthTime.from(OneMonthErrorAlarmTime.from(
															new AgreementOneMonthTime(errorTime),
															new AgreementOneMonthTime(alarmTime)),
														new AgreementOneMonthTime(exceptionLimitTime)));
	}

	public static AgreementTimeOfMonthlyDto from(AgreementTimeOfMonthly domain) {
		AgreementTimeOfMonthlyDto dto = new AgreementTimeOfMonthlyDto();
		if(domain != null) {
			dto.setAgreementTime(domain.getAgreementTime() == null ? 0 : domain.getAgreementTime().valueAsMinutes());
			dto.setAlarmTime(domain.getThreshold().getErAlTime().getAlarm().valueAsMinutes());
			dto.setErrorTime(domain.getThreshold().getErAlTime().getError().valueAsMinutes());
			dto.setExceptionLimitTime(domain.getThreshold().getUpperLimit().v());
		}
		return dto;
	}
}
