package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;

/**
 * 月別実績の乖離時間
 * @author shuichu_ishida
 */
@Getter
public class DivergenceTimeOfMonthly {

	/** 乖離時間 */
	private List<DivergenceTimeOfMonthly> divergenceTimeList;
	
	/**
	 * コンストラクタ
	 */
	public DivergenceTimeOfMonthly(){
		
		this.divergenceTimeList = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param divergenceTimeList 乖離時間
	 * @return 月別実績の乖離時間
	 */
	public static DivergenceTimeOfMonthly of(List<DivergenceTimeOfMonthly> divergenceTimeList){
		
		val domain = new DivergenceTimeOfMonthly();
		domain.divergenceTimeList = divergenceTimeList;
		return domain;
	}
}
