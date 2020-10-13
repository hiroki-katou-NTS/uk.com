package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ReasonsForAgreement;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * 年間の申請内容
 * @author khai.dh
 */
@Getter
@AllArgsConstructor
public class AnnualAppContent extends ValueObject {

	// 対象者
	private final String applicant;

	// 年度
	private final Year year;

	// エラー時間
	private final AgreementOneYearTime errTime;

	// アラーム時間
	@Setter
	private AgreementOneYearTime alarmTime;

	// 申請理由 : 36協定申請理由
	private final ReasonsForAgreement reason;
}
