package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.List;
import java.util.Map;

import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreementTimeOfManagePeriodExport;

/**
 * 管理期間の36協定時間の取得
 * @author shuichu_ishida
 */
public interface AgreementTimeOfManagePeriodPub {

	/**
	 * [NO676]管理期間の36協定時間を取得
	 * 管理期間の36協定時間を取得
	 * @param sid 社員ID
	 * @param year 年度
	 * @return 管理期間の36協定時間リスト
	 */
	public List<AgreementTimeOfManagePeriodExport> get(String sid, Year year);
	
	/**
	 * [NO676]管理期間の36協定時間を取得
	 * @param sids 社員ID(List)
	 * @param year 年度
	 * @return 管理期間の36協定時間リスト
	 */
	public Map<String,List<AgreementTimeOfManagePeriodExport>> get(List<String> sids, Year year);
	
	/**
	 * RequestList 612
	 * [NO.612]年月期間を指定して管理期間の36協定時間を取得する
	 * @param sids 社員ID(List)
	 * @param ymPeriod 年月期間
	 * @return
	 */
	public List<AgreementTimeOfManagePeriodExport> get(List<String> sids, YearMonthPeriod ymPeriod);
}
