package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyAmountMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimeMonth;
import nts.uk.ctx.at.shared.dom.common.anyitem.AnyTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;

/**
 * 集計任意項目
 * @author shuichu_ishida
 */
@Getter
public class AggregateAnyItem implements Cloneable {

	/** 任意項目NO */
	private Integer anyItemNo;
	
	/** 時間 */
	private Optional<AnyTimeMonth> time;
	/** 回数 */
	private Optional<AnyTimesMonth> times;
	/** 金額 */
	private Optional<AnyAmountMonth> amount;
	
	/**
	 * コンストラクタ
	 * @param anyItemNo 任意項目No
	 */
	public AggregateAnyItem(int anyItemNo){
		
		this.anyItemNo = anyItemNo;
		
		this.time = Optional.empty();
		this.times = Optional.empty();
		this.amount = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param anyItemNo　任意項目No
	 * @param time 時間　（NULL可）
	 * @param times 回数　（NULL可）
	 * @param amount 金額　（NULL可）
	 * @return 月の任意項目の計算結果
	 */
	public static AggregateAnyItem of(
			int anyItemNo,
			AnyTimeMonth time,
			AnyTimesMonth times,
			AnyAmountMonth amount){
		
		AggregateAnyItem domain = new AggregateAnyItem(anyItemNo);
		domain.time = Optional.ofNullable(time);
		domain.times = Optional.ofNullable(times);
		domain.amount = Optional.ofNullable(amount);
		return domain;
	}
	
	@Override
	public AggregateAnyItem clone() {
		AggregateAnyItem cloned = new AggregateAnyItem(this.anyItemNo);
		try {
			if (this.time.isPresent()) cloned.time = Optional.of(new AnyTimeMonth(this.time.get().v()));
			if (this.times.isPresent()) cloned.times = Optional.of(new AnyTimesMonth(this.times.get().v().doubleValue()));
			if (this.amount.isPresent()) cloned.amount = Optional.of(new AnyAmountMonth(this.amount.get().v()));
		}
		catch (Exception e){
			throw new RuntimeException("AggregateAnyItem clone error.");
		}
		return cloned;
	}
	
	/**
	 * 日別実績から加算する
	 * @param target 任意項目値（日次）
	 */
	public void addFromDaily(AnyItemValue target){
		
		if (target.getTime().isPresent()){
			if (this.time.isPresent()){
				this.time = Optional.of(this.time.get().addMinutes(target.getTime().get().v()));
			}
			else {
				this.time = Optional.of(new AnyTimeMonth(target.getTime().get().v()));
			}
		}
		if (target.getTimes().isPresent()){
			if (this.times.isPresent()){
				this.times = Optional.of(this.times.get().addTimes(target.getTimes().get().v().doubleValue()));
			}
			else {
				this.times = Optional.of(new AnyTimesMonth(target.getTimes().get().v().doubleValue()));
			}
		}
		if (target.getAmount().isPresent()){
			if (this.amount.isPresent()){
				this.amount = Optional.of(this.amount.get().addAmount(target.getAmount().get().v().intValue()));
			}
			else {
				this.amount = Optional.of(new AnyAmountMonth(target.getAmount().get().v().intValue()));
			}
		}
	}
}
