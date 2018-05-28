package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

/**
 * 実績8計算)以外の場所から計算を呼び出す用のサービス
 * @author keisuke_hoshina
 *
 */
public interface CalculateDailyRecordServiceForOtherCtx {

	public List<IntegrationOfDaily> calculate(List<IntegrationOfDaily> integrationOfDaily);
}
