package nts.uk.ctx.at.shared.dom.workrule.statutoryworktime;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 日別計算用の個人情報
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
public class DailyCalculationPersonalInformation {
	//所定内時間
	private final Optional<DailyTime> predetermineTime;
	@Getter
	//法定労働
	private final DailyTime statutoryWorkTime;
	@Getter
	//労働制
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
