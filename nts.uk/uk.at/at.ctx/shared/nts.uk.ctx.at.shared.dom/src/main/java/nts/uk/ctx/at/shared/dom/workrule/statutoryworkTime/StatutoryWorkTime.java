package nts.uk.ctx.at.shared.dom.workrule.statutoryworkTime;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;

/**
 * フレックス・通常勤務・変形労働の法定労働時間クラスの置き換え用クラス
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
public class StatutoryWorkTime {
	private final Optional<DailyTime> predetermineTime;
	@Getter
	private final DailyTime statutoryWorkTime;
	private final WorkingSystem workingSystem;
	
	/**
	 * 所定労働時間の取得
	 * @return
	 */
	public DailyTime getPredetermineTime() {
		if(workingSystem.isRegularWork() ||workingSystem.isVariableWorkingTimeWork()) {
			throw new RuntimeException("When "+ workingSystem + " in this class, not have predetermineTime");
		}
		return predetermineTime.get();
	}
}
