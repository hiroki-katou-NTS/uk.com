package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実績8計算)以外の場所から計算を呼び出す用のサービス
 * @author keisuke_hoshina
 *
 */
public interface CalculateDailyRecordServiceCenter{

	public List<IntegrationOfDaily> calculate(List<IntegrationOfDaily> integrationOfDaily,DatePeriod date);
}
