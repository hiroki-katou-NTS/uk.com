package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;

public interface AgreementTimeByPeriodAdapter {
	
	/**
	 * 指定期間36協定上限複数月平均時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param yearMonth 指定年月
	 * @return 36協定上限複数月平均時間
	 */
	// RequestList547
	Optional<AgreMaxAverageTimeMulti> maxAverageTimeMulti(String companyId, String employeeId, GeneralDate criteria,
			YearMonth yearMonth);

}
