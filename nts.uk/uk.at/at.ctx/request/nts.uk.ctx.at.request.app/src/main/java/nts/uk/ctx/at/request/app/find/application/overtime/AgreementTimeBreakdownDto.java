package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeBreakdown;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgreementTimeBreakdownDto {
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
	
	
	public static AgreementTimeBreakdownDto fromDomain(AgreementTimeBreakdown param) {
		
		return new AgreementTimeBreakdownDto(
				param.getOverTime().v(),
				param.getTransferOverTime().v(),
				param.getLegalHolidayWorkTime().v(),
				param.getLegalTransferTime().v(),
				param.getIllegalHolidayWorkTime().v(),
				param.getIllegaltransferTime().v(),
				param.getFlexLegalTime().v(),
				param.getFlexIllegalTime().v(),
				param.getWithinPrescribedPremiumTime().v(),
				param.getWeeklyPremiumTime().v(),
				param.getMonthlyPremiumTime().v(),
				param.getTemporaryTime().v());
	}
}
