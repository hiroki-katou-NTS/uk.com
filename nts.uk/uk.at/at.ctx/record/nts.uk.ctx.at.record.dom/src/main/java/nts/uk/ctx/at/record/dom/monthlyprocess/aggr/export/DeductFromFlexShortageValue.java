package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;

/**
 * 戻り値：フレックス不足から年休を欠勤を控除する
 * @author shuichu_ishida
 */
@Getter
public class DeductFromFlexShortageValue {

	/** 月別実績の月の計算 */
	@Setter
	private MonthlyCalculation monthlyCalculation; 
	/** エラー情報リスト */
	private List<MonthlyAggregationErrorInfo> errorInfos;
	
	/**
	 * コンストラクタ
	 */
	public DeductFromFlexShortageValue(){
		this.monthlyCalculation = new MonthlyCalculation();
		this.errorInfos = new ArrayList<>();
	}
}
