package nts.uk.ctx.at.record.pub.monthly.agreement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeBreakdown;

/**
 * 36協定時間内訳
 * @author shuichi_ishida
 */
@Getter
@Builder
@Setter
public class AgreementTimeBreakdownExport {

	/** 残業時間 */
	private int overTime;
	/** 振替残業時間 */
	private int transferOverTime;
	/** 法定内休出時間 */
	private int legalHolidayWorkTime;
	/** 法定内振替時間 */
	private int legalTransferTime;
	/** 法定外休出時間 */
	private int illegalHolidayWorkTime;
	/** 法定外振替時間 */
	protected int illegaltransferTime;
	/** 法定内フレックス超過時間 */
	protected int flexLegalTime;
	/** 法定外フレックス超過時間 */
	protected int flexIllegalTime;
	/** 所定内割増時間 */
	protected int withinPrescribedPremiumTime;
	/** 週割増合計時間 */
	protected int weeklyPremiumTime;
	/** 月間割増合計時間 */
	protected int monthlyPremiumTime;
	/** 臨時時間 */
	protected int temporaryTime;
	
	public static AgreementTimeBreakdownExport copy(AgreementTimeBreakdown domain) {
		
		return AgreementTimeBreakdownExport.builder()
				.overTime(domain.getOverTime().valueAsMinutes())
				.transferOverTime(domain.getTransferOverTime().valueAsMinutes())
				.legalHolidayWorkTime(domain.getLegalHolidayWorkTime().valueAsMinutes())
				.legalTransferTime(domain.getLegalTransferTime().valueAsMinutes())
				.illegalHolidayWorkTime(domain.getIllegalHolidayWorkTime().valueAsMinutes())
				.illegaltransferTime(domain.getIllegaltransferTime().valueAsMinutes())
				.flexLegalTime(domain.getFlexLegalTime().valueAsMinutes())
				.flexIllegalTime(domain.getFlexIllegalTime().valueAsMinutes())
				.withinPrescribedPremiumTime(domain.getWithinPrescribedPremiumTime().valueAsMinutes())
				.weeklyPremiumTime(domain.getWeeklyPremiumTime().valueAsMinutes())
				.monthlyPremiumTime(domain.getMonthlyPremiumTime().valueAsMinutes())
				.temporaryTime(domain.getTemporaryTime().valueAsMinutes())
				.build();
	}
	
}
