package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;

/**
 * 社員の法定労働時間
 * @author lan_lt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LegalWorkTimeOfEmployee {
	//社員ID
	private String sid;
	//週の時間
	private Optional<MonthlyEstimateTime> weeklyEstimateTime;
	//月の時間
	private MonthlyEstimateTime monthlyEstimateTime;

	/**
	 * フレックス時間勤務を作る
	 * @param sid
	 * @param monthlyEstimateTime
	 * @return
	 */
	public static LegalWorkTimeOfEmployee createFlexWorkTime(String sid, MonthlyEstimateTime monthlyEstimateTime) {
		
		return new LegalWorkTimeOfEmployee(sid, Optional.empty(), monthlyEstimateTime);
	}
	
	/**
	 * 作る
	 * @param sid
	 * @param weeklyEstimateTime
	 * @param monthlyEstimateTime
	 * @return
	 */
	public static LegalWorkTimeOfEmployee create(String sid, MonthlyEstimateTime weeklyEstimateTime
			, MonthlyEstimateTime monthlyEstimateTime) {
		return new LegalWorkTimeOfEmployee(sid, Optional.of(weeklyEstimateTime), monthlyEstimateTime);
	}
}
