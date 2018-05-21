package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.anyitem;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValue;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;

/**
 * 月の任意項目の計算結果
 * @author shuichu_ishida
 */
@Getter
@Setter
public class CalcResultMonthOfAnyItem {

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
	public CalcResultMonthOfAnyItem(int anyItemNo){
		
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
	public static CalcResultMonthOfAnyItem of(
			int anyItemNo,
			AnyTimeMonth time,
			AnyTimesMonth times,
			AnyAmountMonth amount){
		
		CalcResultMonthOfAnyItem domain = new CalcResultMonthOfAnyItem(anyItemNo);
		domain.time = time;
		domain.times = times;
		domain.amount = amount;
		return domain;
	}
	
	/**
	 * 日別実績から加算する
	 * @param target 任意項目値（日次）
	 */
	public void addFromDaily(AnyItemValue target){
		
		if (target.getTime().isPresent()){
			if (this.time == null){
				this.time = new AnyTimeMonth(target.getTime().get().v());
			}
			else {
				this.time = this.time.addMinutes(target.getTime().get().v());
			}
		}
		if (target.getTimes().isPresent()){
			if (this.times == null){
				this.times = new AnyTimesMonth(0.0);
				//this.times = new AnyTimesMonth(target.getTimes().get().v());
			}
			else {
				this.times = this.times.addTimes(0.0);
				//this.times = this.times.addTimes(target.getTimes().get().v());
			}
		}
		if (target.getAmount().isPresent()){
			if (this.amount == null){
				this.amount = new AnyAmountMonth(target.getAmount().get().v().intValue());
			}
			else {
				this.amount = this.amount.addAmount(target.getAmount().get().v().intValue());
			}
		}
	}
}
