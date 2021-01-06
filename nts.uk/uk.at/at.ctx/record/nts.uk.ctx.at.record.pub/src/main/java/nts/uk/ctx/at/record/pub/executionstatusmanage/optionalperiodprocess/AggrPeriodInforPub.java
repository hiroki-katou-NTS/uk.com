package nts.uk.ctx.at.record.pub.executionstatusmanage.optionalperiodprocess;

import java.util.List;

import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;

public interface AggrPeriodInforPub {

	/**
	 * ドメインモデル「任意期間集計エラーメッセージ情報」を取得する
	 * @param execId
	 * @return
	 */
	List<AggrPeriodInfor> findAggrPeriodInfor(String execId);
	
}
