package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;

import java.util.Optional;

/**
 * 申請作成結果
 * @author khai.dh
 */
@Getter
@Setter
@AllArgsConstructor
public class AppCreationResult {
	//社員ID
	private String empId;

	//結果区分
	private ResultType resultDiff;

	// 永続化処理
	private final Optional<AtomTask> atomTask;

	// ３６協定１ヶ月時間
	private Optional<AgreementOneMonthTime> oneMonth36Agr;

	// 36協定1年間時間
	private Optional<AgreementOneYearTime> oneYear36Agr;
}
