package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;

/** 36協定年間時間（Temporary） */
@Data
@Builder
public class AgreementTimeYearTemporary {

	/** 36協定対象時間: 36協定1年間時間 */
	@Builder.Default
	private AgreementOneYearTime agreementTime = new AgreementOneYearTime(0);

	/** 法定上限対象時間: 36協定1年間時間 */
	@Builder.Default
	private AgreementOneYearTime legalLimitTime = new AgreementOneYearTime(0);
	
	public void add(AgreementTimeYearTemporary target) {
		this.agreementTime = this.agreementTime.addMinutes(target.agreementTime.valueAsMinutes());
		this.legalLimitTime = this.legalLimitTime.addMinutes(target.legalLimitTime.valueAsMinutes());
	}
	
	public static AgreementTimeYearTemporary map(AgreementTimeOfManagePeriod agreementTime) {
		
		return AgreementTimeYearTemporary.builder()
				.agreementTime(new AgreementOneYearTime(agreementTime.getAgreementTime().getAgreementTime().valueAsMinutes()))
				.legalLimitTime(new AgreementOneYearTime(agreementTime.getLegalMaxTime().getAgreementTime().valueAsMinutes()))
				.build();
	}
}
