package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * 年間超過回数の取得
 * @author shuichu_ishida
 */
public interface GetExcessTimesYear {

	/**
	 * 年間超過回数の取得
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @return 年間超過回数
	 */
	AgreementExcessInfo algorithm(String employeeId, Year year);
}
