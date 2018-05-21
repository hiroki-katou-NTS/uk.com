package nts.uk.ctx.at.record.dom.monthly.anyitem;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;

/**
 * 集計任意項目
 * @author shuichu_ishida
 */
@Getter
public class AggregateAnyItem implements Cloneable {

	/** 任意項目NO */
	private Integer anyItemNo;
	
	/** 時間 */
	private AnyTimeMonth time;
	/** 回数 */
	private AnyTimesMonth times;
	/** 金額 */
	private AnyAmountMonth amount;
	
	/**
	 * コンストラクタ
	 * @param anyItemNo 任意項目No
	 */
	public AggregateAnyItem(int anyItemNo){
		
		this.anyItemNo = anyItemNo;
		
		this.time = null;
		this.times = null;
		this.amount = null;
	}
	
	/**
	 * ファクトリー
	 * @param anyItemNo　任意項目No
	 * @param time 時間
	 * @param times 回数
	 * @param amount 金額
	 * @return 月の任意項目の計算結果
	 */
	public static AggregateAnyItem of(
			int anyItemNo,
			AnyTimeMonth time,
			AnyTimesMonth times,
			AnyAmountMonth amount){
		
		AggregateAnyItem domain = new AggregateAnyItem(anyItemNo);
		domain.time = time;
		domain.times = times;
		domain.amount = amount;
		return domain;
	}
	
	@Override
	public AggregateAnyItem clone() {
		AggregateAnyItem cloned = new AggregateAnyItem(this.anyItemNo);
		try {
			cloned.time = new AnyTimeMonth(this.time.v());
			cloned.times = new AnyTimesMonth(this.times.v().doubleValue());
			cloned.amount = new AnyAmountMonth(this.amount.v());
		}
		catch (Exception e){
			throw new RuntimeException("AggregateAnyItem clone error.");
		}
		return cloned;
	}
}
