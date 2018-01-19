package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;

/**
 * 月別実績の加給時間
 * @author shuichu_ishida
 */
@Getter
public class BonusPayTimeOfMonthly {

	/** 加給時間 */
	private List<AggregateBonusPayTime> bonusPayTime;
	
	/**
	 * コンストラクタ
	 */
	public BonusPayTimeOfMonthly(){
		
		this.bonusPayTime = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param bonusPayTime 加給時間
	 * @return 月別実績の加給時間
	 */
	public static BonusPayTimeOfMonthly of(List<AggregateBonusPayTime> bonusPayTime){
		
		val domain = new BonusPayTimeOfMonthly();
		domain.bonusPayTime = bonusPayTime;
		return domain;
	}
}
