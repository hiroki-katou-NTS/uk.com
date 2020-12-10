package nts.uk.ctx.at.shared.dom.statutory.worktime;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;

/**
 * 社員の法定労働時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（New）.社員の法定労働時間
 * @author chungnt
 *
 */

@Getter
public class LegalWorkingHoursOfEmployees {

	//	社員ID
	private String sid;
	
	//	週の時間
	private Optional<MonthlyEstimateTime> weekTime;
	
	//	月の時間
	private MonthlyEstimateTime moonTime;
	
	/**
	 * [C-1] フレックス時間勤務を作る
	 * @param sid
	 * @param moonTime
	 */
	public LegalWorkingHoursOfEmployees(String sid, MonthlyEstimateTime moonTime) {
		super();
		this.sid = sid;
		this.weekTime = Optional.empty();
		this.moonTime = moonTime;
	}
	
	/**
	 * [C-2] 作る
	 * @param sid
	 * @param weekTime
	 * @param moonTime
	 */
	public LegalWorkingHoursOfEmployees(String sid, Optional<MonthlyEstimateTime> weekTime, MonthlyEstimateTime moonTime) {
		super();
		this.sid = sid;
		this.weekTime = weekTime;
		this.moonTime = moonTime;
	}
}
