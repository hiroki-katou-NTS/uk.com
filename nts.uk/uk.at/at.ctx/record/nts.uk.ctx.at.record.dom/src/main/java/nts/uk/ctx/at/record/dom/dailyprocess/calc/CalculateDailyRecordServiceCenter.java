package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実績8計算)以外の場所から計算を呼び出す用のサービス
 * @author keisuke_hoshina
 *
 */
public interface CalculateDailyRecordServiceCenter{

	//計算
	public List<IntegrationOfDaily> calculate(List<IntegrationOfDaily> integrationOfDaily);
	
	//エラーチェック
	public List<IntegrationOfDaily> errorCheck(List<IntegrationOfDaily> integrationList);
}
