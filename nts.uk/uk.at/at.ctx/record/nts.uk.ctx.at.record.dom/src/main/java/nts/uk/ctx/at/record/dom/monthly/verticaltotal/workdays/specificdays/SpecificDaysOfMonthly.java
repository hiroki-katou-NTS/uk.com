package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;

/**
 * 月別実績の特定日数
 * @author shuichu_ishida
 */
@Getter
public class SpecificDaysOfMonthly {

	/** 特定日数 */
	private List<AggregateSpecificDays> specificDays;
	
	/**
	 * コンストラクタ
	 */
	public SpecificDaysOfMonthly(){
		
		this.specificDays = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param specificDays 特定日数
	 * @return 月別実績の特定日数
	 */
	public static SpecificDaysOfMonthly of(List<AggregateSpecificDays> specificDays){
		
		val domain = new SpecificDaysOfMonthly();
		domain.specificDays = specificDays;
		return domain;
	}
}
