package nts.uk.ctx.at.record.pub.monthly.agreement.export;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;

@Data
@Builder
@AllArgsConstructor
public class AgreementTimeYearExport {

	/** 法定上限対象時間 */
	private AgreementTimeOfMonthlyExport limitTime;
	/** 36協定対象時間 */
	private AgreementTimeOfMonthlyExport recordTime;
	/** 状態 */
	private int status;
	
	public static AgreementTimeYearExport copy(AgreementTimeYear domain) {
		
		return AgreementTimeYearExport.builder()
				.limitTime(AgreementTimeOfMonthlyExport.copy(domain.getLimitTime()))
				.recordTime(AgreementTimeOfMonthlyExport.copy(domain.getRecordTime()))
				.status(domain.getStatus().value)
				.build();
	}
}
