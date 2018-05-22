package nts.uk.ctx.at.record.pub.monthlyprocess.agreement;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 36協定時間の取得
 * @author shuichu_ishida
 */
public interface GetAgreementTimePub {

	/**
	 * 36協定時間の取得
	 * @param companyId 会社ID
	 * @param employeeIds 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @return 36協定時間一覧
	 */
	// RequestList287
	List<AgreementTimeExport> get(String companyId, List<String> employeeIds, YearMonth yearMonth, ClosureId closureId);
}
