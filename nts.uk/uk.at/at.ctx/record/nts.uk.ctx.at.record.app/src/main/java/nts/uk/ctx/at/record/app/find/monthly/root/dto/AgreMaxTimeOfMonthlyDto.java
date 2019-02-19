package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreMaxTimeOfMonthly;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の36協定上限時間 */
public class AgreMaxTimeOfMonthlyDto implements ItemConst {

	/** 36協定時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = AGREEMENT, layout = LAYOUT_A)
	private int agreementTime;

	/** 上限時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = UPPER_LIMIT, layout = LAYOUT_B)
	private int maxTime;

	/** 状態 */
	@AttendanceItemValue(type = ValueType.NUMBER)
	@AttendanceItemLayout(jpPropertyName = STATE, layout = LAYOUT_C)
	private int status;
	
	public AgreMaxTimeOfMonthly toDomain() {
		return AgreMaxTimeOfMonthly.of(new AttendanceTimeMonth(agreementTime),
										new LimitOneMonth(maxTime),
										ConvertHelper.getEnum(status, AgreMaxTimeStatusOfMonthly.class));
	}
	
	public static AgreMaxTimeOfMonthlyDto from(AgreMaxTimeOfMonthly domain) {
		AgreMaxTimeOfMonthlyDto dto = new AgreMaxTimeOfMonthlyDto();
		if(domain != null) {
			dto.setAgreementTime(domain.getAgreementTime() == null ? 0 : domain.getAgreementTime().valueAsMinutes());
			dto.setMaxTime(domain.getMaxTime().valueAsMinutes());
			dto.setStatus(domain.getStatus() == null ? 0 : domain.getStatus().value);
		}
		return dto;
	}
}