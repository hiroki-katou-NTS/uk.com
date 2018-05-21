package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の36協定時間 */
public class AgreementTimeOfMonthlyDto {

	/** 36協定時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "36協定時間", layout = "A")
	private Integer agreementTime;

	/** 限度アラーム時間: ３６協定１ヶ月時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "限度アラーム時間", layout = "B")
	private Integer limitAlarmTime;

	/** 限度エラー時間: ３６協定１ヶ月時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "限度エラー時間", layout = "C")
	private Integer limitErrorTime;

	/** AgreementTimeStatusOfMonthly */
	/** 状態: 月別実績の36協定時間状態 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "状態", layout = "D")
	private int status;

	/** 特例限度アラーム時間: ３６協定１ヶ月時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "特例限度アラーム時間", layout = "E")
	private Integer exceptionLimitAlarmTime;

	/** 特例限度エラー時間: ３６協定１ヶ月時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "特例限度エラー時間", layout = "F")
	private Integer exceptionLimitErrorTime;

	public AgreementTimeOfMonthly toDomain() {
		return AgreementTimeOfMonthly.of(agreementTime == null ? null : new AttendanceTimeMonth(agreementTime),
										limitErrorTime == null ? null : new LimitOneMonth(limitErrorTime),
										limitAlarmTime == null ? null : new LimitOneMonth(limitAlarmTime),
										exceptionLimitErrorTime == null ? Optional.empty()
												: Optional.of(new LimitOneMonth(exceptionLimitErrorTime)),
										exceptionLimitAlarmTime == null ? Optional.empty()
												: Optional.of(new LimitOneMonth(exceptionLimitAlarmTime)),
										ConvertHelper.getEnum(status, AgreementTimeStatusOfMonthly.class));
	}
	
	public static AgreementTimeOfMonthlyDto from(AgreementTimeOfMonthly domain) {
		AgreementTimeOfMonthlyDto dto = new AgreementTimeOfMonthlyDto();
		if(domain != null) {
			dto.setAgreementTime(domain.getAgreementTime() == null ? null : domain.getAgreementTime().valueAsMinutes());
			dto.setExceptionLimitAlarmTime(from(domain.getExceptionLimitAlarmTime().orElse(null)));
			dto.setExceptionLimitErrorTime(from(domain.getExceptionLimitErrorTime().orElse(null)));
			dto.setLimitAlarmTime(from(domain.getLimitAlarmTime()));
			dto.setLimitErrorTime(from(domain.getLimitErrorTime()));
			dto.setStatus(domain.getStatus() == null ? 0 : domain.getStatus().value);
		}
		return dto;
	}
	
	private static Integer from(LimitOneMonth domain) {
		return domain == null ? null : domain.valueAsMinutes();
	}
}
