package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 月別実績集計の特定日カウント
 * @author HoangNDH
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecTotalCountMonthly {
	
	// 連続勤務の日でもカウントする
	private boolean continuousCount;
	// 勤務日ではない日でもカウントする
	private boolean notWorkCount;
	// 計算対象外のカウント条件
	private SpecCountNotCalcSubject specCount;
}
