package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeBreakdown;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgreementTimeBreakdownCommand {
	/** 残業時間 */
	public Integer overTime;
	/** 振替残業時間 */
	public Integer transferOverTime;
	/** 法定内休出時間 */
	public Integer legalHolidayWorkTime;
	/** 法定内振替時間 */
	public Integer legalTransferTime;
	/** 法定外休出時間 */
	public Integer illegalHolidayWorkTime;
	/** 法定外振替時間 */
	public Integer illegaltransferTime;
	/** 法定内フレックス超過時間 */
	public Integer flexLegalTime;
	/** 法定外フレックス超過時間 */
	public Integer flexIllegalTime;
	/** 所定内割増時間 */
	public Integer withinPrescribedPremiumTime;
	/** 週割増合計時間 */
	public Integer weeklyPremiumTime;
	/** 月間割増合計時間 */
	public Integer monthlyPremiumTime;
	/** 臨時時間 */
	public Integer temporaryTime;
	
	
	public AgreementTimeBreakdown toDomain() {
		
		return AgreementTimeBreakdown.of(
				new AttendanceTimeMonth(overTime),
				new AttendanceTimeMonth(transferOverTime),
				new AttendanceTimeMonth(illegalHolidayWorkTime),
				new AttendanceTimeMonth(illegaltransferTime),
				new AttendanceTimeMonth(flexLegalTime),
				new AttendanceTimeMonth(flexIllegalTime),
				new AttendanceTimeMonth(withinPrescribedPremiumTime),
				new AttendanceTimeMonth(weeklyPremiumTime),
				new AttendanceTimeMonth(monthlyPremiumTime),
				new AttendanceTimeMonth(temporaryTime),
				new AttendanceTimeMonth(legalHolidayWorkTime),
				new AttendanceTimeMonth(legalTransferTime));
	}
}
