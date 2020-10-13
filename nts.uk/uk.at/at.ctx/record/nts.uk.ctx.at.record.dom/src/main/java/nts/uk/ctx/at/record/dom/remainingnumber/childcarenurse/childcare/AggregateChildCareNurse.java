package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;

/**
 * 子の看護介護休暇 集計期間
  * @author yuri_tamakoshi
 */

public class AggregateChildCareNurse {
	/** 期間(List) */
	private List<AggregateChildCareNurseWork> period;

	/**
	 * コンストラクタ
	 */
	public AggregateChildCareNurse() {
		this.period =  new ArrayList<>();
	}
	/**
	 * ファクトリー
	 * @param period 集計期間
	 * @return 子の看護介護休暇 集計期間
	 */
	public static AggregateChildCareNurse of(
			 List<AggregateChildCareNurseWork> period){

		AggregateChildCareNurse domain = new AggregateChildCareNurse();
		domain.period = period;
		return domain;
	}
}
