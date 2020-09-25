package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * 月間の申請内容
 * @author khai.dh
 */
@Getter
@AllArgsConstructor
public class MonthlyAppContent extends ValueObject {
	// 対象者
	private String applicant;

	// 年月度
	private YearMonth ym;

	// エラー時間
	private AgreementOneMonthTime errTime;

	// アラーム時間
	@Setter
	private AgreementOneMonthTime alarmTime;

	// 申請理由 : 36協定申請理由
	private ReasonsForAgreement reason;
}
