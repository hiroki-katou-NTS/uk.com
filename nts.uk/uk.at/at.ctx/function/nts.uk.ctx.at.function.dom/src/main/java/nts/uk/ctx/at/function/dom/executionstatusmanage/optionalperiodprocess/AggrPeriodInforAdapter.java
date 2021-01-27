package nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess;

import java.util.List;

public interface AggrPeriodInforAdapter {

	/**
	 * ドメインモデル「任意期間集計エラーメッセージ情報」を取得する
	 * @param execId
	 * @return
	 */
	List<AggrPeriodInforImported> findAggrPeriodInfor(String execId);
	
}
