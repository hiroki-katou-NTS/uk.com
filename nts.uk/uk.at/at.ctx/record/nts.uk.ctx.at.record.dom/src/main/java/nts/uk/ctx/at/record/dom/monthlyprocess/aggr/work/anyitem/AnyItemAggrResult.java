package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.anyitem;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;

/**
 * 任意項目集計結果
 * @author shuichu_ishida
 */
@Getter
public class AnyItemAggrResult {

	/** 任意項目NO */
	private int optionalItemNo;
	/** 時間 */
	private AnyTimeMonth anyTime;
	/** 回数 */
	private AnyTimesMonth anyTimes;
	/** 金額 */
	private AnyAmountMonth anyAmount;
	
	private AnyItemAggrResult(){
		this.optionalItemNo = 0;
		this.anyTime = null;
		this.anyTimes = null;
		this.anyAmount = null;
	}

	/**
	 * ファクトリー
	 * @param optionalItemNo 任意項目NO
	 * @param anyTime 時間
	 * @param anyTimes 回数
	 * @param anyAmount 金額
	 * @return 任意項目集計結果
	 */
	public static AnyItemAggrResult of(
			int optionalItemNo,
			AnyTimeMonth anyTime,
			AnyTimesMonth anyTimes,
			AnyAmountMonth anyAmount){

		AnyItemAggrResult domain = new AnyItemAggrResult();
		domain.optionalItemNo = optionalItemNo;
		domain.anyTime = anyTime;
		domain.anyTimes = anyTimes;
		domain.anyAmount = anyAmount;
		return domain;
	}
}
