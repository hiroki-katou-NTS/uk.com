package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * 年間の申請内容
 * @author khai.dh
 */
@Getter
@AllArgsConstructor
public class AnnualAppContent extends ValueObject {

	// 対象者
	private String applicant;

	// 年月度
	private Year year;

	// エラー時間
	private AgreementOneMonthTime errTime;

	// アラーム時間
	private AgreementOneMonthTime alarmTime;

	// 申請理由 : 36協定申請理由
	private SpecialProvisionsOfAgreement reason;
}
