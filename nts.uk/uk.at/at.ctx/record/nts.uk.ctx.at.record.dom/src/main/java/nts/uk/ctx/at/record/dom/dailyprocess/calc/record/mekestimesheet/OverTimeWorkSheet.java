package nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.DailyOverTimeWork;
import nts.uk.ctx.at.record.dom.daily.OverTimeWorkOfDaily;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.OverWorkSet.StatutoryOverWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;

/**
 * 残業時間帯
 * @author keisuke_hoshina
 *
 */
public class OverTimeWorkSheet {
	
	private OverTimeWorkOfDaily dailyOverWorkTime; 
	
	private StatutoryOverWorkSet statutoryOverWorkSet;
	
	private DailyTime dailyTime;
	
	private  OverTimeWorkSheet(OverTimeWorkOfDaily dailyOverWork) {
		this.dailyOverWorkTime = dailyOverWork;
	}
	
	
	/**
	 * 残業枠分ループし残業枠時間帯の作成
	 * @param overTimeHourSet
	 * @param workingSystem
	 * @return
	 */
	public OverTimeWorkSheet createOverWorkFrame(List<OverTimeHourSet> overTimeHourSet,WorkingSystem workingSystem) {
		return new OverTimeWorkSheet(OverTimeWorkOfDaily.of(overTimeHourSet));
	}
	
	/**
	 * 変形基準内時間の計算をするか判定
	 * @param workingSystem 労働制
	 */
	public void dicisionCalcVariableWork(WorkingSystem workingSystem) {
		if(workingSystem.isVariableWorkingTimeWork()) {
			calcOverWorkTime(daily - // 所定労働時間);
		}
	}
	
	/**
	 * 法定内残業時間の計算をするか判定
	 * @param statutoryOverWorkSet 法定内残業設定クラス
	 */
	public void calcStatutory(StatutoryOverWorkSet statutoryOverWorkSet) {
		if(statutoryOverWorkSet.isAutoCalcStatutoryOverWork()) {
			calcOverWorkTime(daily - // 就業時間(割増時間計算用));
		}
	}
	
	/**
	 * 残業時間枠の計算
	 */
	public void calcOverWorkTime(int limitTime) {
		List<OverTimeWorkFrameTimeSheet> copyList = dailyOverWorkTime.getOverTimeWorkFrameTimeSheet();
		dailyOverWorkTime.getOverTimeWorkFrameTimeSheet().clear();
		
		for(OverTimeWorkFrameTimeSheet overWorkFrameTimeSheet :copyList){
			
			if(overWorkFrameTimeSheet..OverWorkFrameTime.limitgreaterhanOverWorkTime(limitTime)) {
				dailyOverWorkTime.getOverWorkTimeSheet().add(overWorkFrameTimeSheet.createOverWorkFramTimeSheet());
			}
			else {
				dailyOverWorkTime.getOverTimeWorkFrameTimeSheet().add(overWorkFrameTimeSheet..devideAfterJudgeWorkTime(limitTime));
				dailyOverWorkTime.getOverTimeWorkFrameTimeSheet().add(overWorkFrameTimeSheet.devideAfterJudgeOutOfWorkTime(limitTime));
			}
			limitTime = overWorkFrameTimeSheet.OverWorkFrameTime.calcLimit(limitTime);
			if(limitTime ==0) {
				break;
			}
		}
	}
}
