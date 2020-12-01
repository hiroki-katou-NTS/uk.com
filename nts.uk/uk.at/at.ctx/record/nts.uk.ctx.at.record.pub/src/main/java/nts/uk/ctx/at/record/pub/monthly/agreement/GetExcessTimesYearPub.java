package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreementExcessInfoExport;

/**
 * 年間超過回数の取得
 * @author shuichi_ishida
 */
public interface GetExcessTimesYearPub {

	/**
	 * 年間超過回数の取得
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @return 年間超過回数
	 */
	// RequestList458
	AgreementExcessInfoExport algorithm(String employeeId, Year year);
	
	
	/**
	 * 年間超過回数の取得
	 * @param employeeId List 社員ID
	 * @param year 年度
	 * @return 年間超過回数
	 */
	Map<String, AgreementExcessInfoExport> algorithm(List<String> employeeId, Year year);
	
	/**
	 * 年間超過回数と残数の取得
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @param agreementOperationSetting 36協定運用設定
	 * @return 年間超過回数
	 */
	// RequestList555
	AgreementExcessInfoExport andRemainTimes(String employeeId, Year year, GeneralDate baseDate);
}
