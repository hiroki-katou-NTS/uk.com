package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 月別実績集計の特定日カウント
 * @author HoangNDH
 *
 */
@Data
@NoArgsConstructor
public class SpecTotalCountMonthly {
	
	// 連続勤務の日でもカウントする
	private boolean continuousCount;
	// 勤務日ではない日でもカウントする
	private boolean notWorkCount;
	// 計算対象外のカウント条件
	private SpecCountNotCalcSubject specCount;
	
	public static SpecTotalCountMonthly of(boolean continuousCount, 
			boolean notWorkCount, SpecCountNotCalcSubject specCount) {
		
		SpecTotalCountMonthly result = new SpecTotalCountMonthly();
		
		result.continuousCount = continuousCount;
		result.notWorkCount = notWorkCount;
		result.specCount = specCount;
		
		return result;
	}
}
