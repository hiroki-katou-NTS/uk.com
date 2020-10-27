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
	/** 社員ID */
	private String sid;
	/** 週の時間 */
	private Optional<MonthlyEstimateTime> weeklyEstimateTime;
	/** 月の時間 */
	private MonthlyEstimateTime monthlyEstimateTime;

	/**
	 * 月の時間のみで作る
	 * @param sid 社員ID
	 * @param monthlyEstimateTime 月間時間
	 * @return
	 */
	public static LegalWorkTimeOfEmployee createOnlyMonthTime(String sid, MonthlyEstimateTime monthlyEstimateTime) {
		
		return new LegalWorkTimeOfEmployee(sid, Optional.empty(), monthlyEstimateTime);
	}
	
	/**
	 * 作る
	 * @param sid 社員ID
	 * @param weeklyEstimateTime 月間時間
	 * @param monthlyEstimateTime 月間時間
	 * @return
	 */
	public static LegalWorkTimeOfEmployee create(String sid, MonthlyEstimateTime weeklyEstimateTime
			, MonthlyEstimateTime monthlyEstimateTime) {
		return new LegalWorkTimeOfEmployee(sid, Optional.of(weeklyEstimateTime), monthlyEstimateTime);
	}
}
