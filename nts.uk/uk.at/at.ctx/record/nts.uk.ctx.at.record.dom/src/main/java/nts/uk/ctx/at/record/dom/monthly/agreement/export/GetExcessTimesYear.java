package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * 年間超過回数の取得
 * @author shuichi_ishida
 */
public interface GetExcessTimesYear {

	/**
	 * 年間超過回数の取得
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @return 年間超過回数
	 */
	AgreementExcessInfo algorithm(String employeeId, Year year);
	
	/**
	 * 年間超過回数と残数の取得
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @param agreementOperationSetting 36協定運用設定
	 * @return 年間超過回数
	 */
	AgreementExcessInfo andRemainTimes(String employeeId, Year year,
			Optional<AgreementOperationSetting> agreementOperationSetting);
}
