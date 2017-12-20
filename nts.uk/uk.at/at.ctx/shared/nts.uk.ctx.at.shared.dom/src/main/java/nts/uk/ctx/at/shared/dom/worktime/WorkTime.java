package nts.uk.ctx.at.shared.dom.worktime;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexworkset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime_old.SiftCode;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeNote;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 *  就業時間帯の設定
 * @author Doan Duy Hung
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class WorkTime extends AggregateRoot {

	private SiftCode siftCD;
	
	private String companyID;
	
	private WorkTimeNote note;

	private WorkTimeDivision workTimeDivision;
	
	private UseSetting dispAtr;

	private WorkTimeDisplayName workTimeDisplayName;
	
	public WorkTime(SiftCode siftCD, String companyID, WorkTimeNote note, WorkTimeDivision workTimeDivision,
			UseSetting dispAtr, WorkTimeDisplayName workTimeDisplayName) {
		super();
		this.siftCD = siftCD;
		this.companyID = companyID;
		this.note = note;
		this.workTimeDivision = workTimeDivision;
		this.dispAtr = dispAtr;
		this.workTimeDisplayName = workTimeDisplayName;
	}
	
	
//	/**
//	 * 遅刻時間の計算範囲を取得する
//	 * @param goWorkTime
//	 * @return 遅刻の計算開始時間
//	 */
//	public TimeSpanForCalc getLateTimeCalcRange(TimeWithDayAttr goWorkTime,int workNo) {
//		if(workTimeDivision.getWorkTimeDailyAtr().isFlex()&& flexWorkSetting.getCoreTimeSetting().getUse().isUse()) {
//			return new TimeSpanForCalc(flexWorkSetting.getCoreTimeSetting().getCoreTime().getStart(),goWorkTime);
//		}
//		return new TimeSpanForCalc(predetermineTimeSet.getSpecifiedTimeSheet().getMatchWorkNoTimeSheet(workNo).getStartTime(),goWorkTime);
//	}
//	
//	
//	/**
//	 * 早退時間の計算範囲を取得する
//	 * @param leaveWorkTime
//	 * @param workNo
//	 * @return
//	 */
//	public TimeSpanForCalc getleaveEarlyTimeCalcRange(TimeWithDayAttr leaveWorkTime,int workNo) {
//		if(workTimeDivision.getWorkTimeDailyAtr().isFlex()&& flexWorkSetting.getCoreTimeSetting().getUse().isUse()) {
//			return new TimeSpanForCalc(leaveWorkTime,flexWorkSetting.getCoreTimeSetting().getCoreTime().getEnd());
//		}
//		return new TimeSpanForCalc(leaveWorkTime,predetermineTimeSet.getSpecifiedTimeSheet().getMatchWorkNoTimeSheet(workNo).getEndTime());
//	}
//	
	
//	/**
//	 * 1回目の所定時間の開始時刻を取得
//	 * @return 所定時間の開始時刻
//	 */
//	public TimeWithDayAttr onePredetermineStartTime(List<DeductionItemOfTimeSheet> a) {
//		return this.predetermineTimeSet.getSpecifiedTimeSheet().getTimeSheets().get(0).getStartTime();
//	}
	
	public void getBreakTimeSheet() {
		if(workTimeDivision.getWorkTimeDailyAtr().isFlex() || workTimeDivision.getWorkTimeMethodSet().isFluidWork()) {
			
		}
		else {
			
		}
	}

}
