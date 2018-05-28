package nts.uk.ctx.at.record.dom.dailyprocess.calc;

/**
 * ドメインサービス：日別実績計算処理
 * @author shuichu_ishida
 */
public interface CalculateDailyRecordService {

	/**
	 * 勤務情報を取得して計算
	 * @param companyId 会社ID
	 * @param placeId 職場ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param targetDate 対象年月日
	 * @param integrationOfDaily 日別実績(Work) 
	 * @param companyCommonSetting 
	 * @return 日別実績(Work)
	 */
	public IntegrationOfDaily calculate(IntegrationOfDaily integrationOfDaily, ManagePerCompanySet companyCommonSetting);
}
