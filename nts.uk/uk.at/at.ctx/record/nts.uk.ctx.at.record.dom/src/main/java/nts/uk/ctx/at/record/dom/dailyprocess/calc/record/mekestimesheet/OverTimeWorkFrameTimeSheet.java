package nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet;

import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;

/**
 * 残業枠時間帯
 * @author keisuke_hoshina
 *
 */
public class OverTimeWorkFrameTimeSheet extends CalculationTimeSheet{
	/**残業時間帯NO**/
	private final int frameNo;
	
	private final OverTimeWorkFrameTime overWorkFrameTime;
	
	
	public OverTimeWorkFrameTimeSheet(
			TimeSpanWithRounding timesheet
			,int frameNo
			,TimeSpanForCalc calculationTimeSheet
			,OverTimeWorkFrameTime overTimeWorkFrameTime) {
		super(timesheet,calculationTimeSheet);
		this.frameNo = frameNo;
		this.overWorkFrameTime = overTimeWorkFrameTime;
	}
	
	/**
	 *残業枠時間帯の作成
	 * @param frameNo
	 * @param timeSpanWithRounding
	 * @return
	 */
	public static OverTimeWorkFrameTimeSheet createOverWorkFramTimeSheet(TimeSpanWithRounding timeSpanWithRounding) {
		return new OverTimeWorkFrameTimeSheet(getFrameNO(),timeSpanWithRounding.startValue(),timeSpanWithRounding.endValue());
	}
	/**
	 * 残業枠時間帯の作成(法定内時間帯と判断された場合)
	 * @param frameNo
	 * @param timeSpanWithRounding
	 * @return
	 */
	public static OverTimeWorkFrameTimeSheet createOverWorkFramTimeSheet() {
		return new OverTimeWorkFrameTimeSheet(getFrameNO(),getStart(),getEnd(),true);
	}
	
	private int getStart() {
		return start;
	}
	
	private int getEnd() {
		return end;
	}
	
	private int getFrameNO() {
		return frameNo;
	}
	/**
	 * 分割後　就内と判断された場合
	 * @param frameNo
	 * @return
	 */
	public static OverWorkFrameTimeSheet devideAfterJudgeWorkTime(int limit) {
		return new OverWorkFrameTimeSheet(getFrameNO(),getStart(),getStart()+limit,true);
	}
	
	/**
	 * 分割後はみ出した時間
	 * @param frameNo
	 * @return
	 */
	public static OverWorkFrameTimeSheet devideAfterJudgeOutOfWorkTime(int limit) {
		return new OverWorkFrameTimeSheet(getFrameNO(),getStart()+limit,getEnd(),false);
	}
	public OverTimeHourSet(
			int workingHoursTimeNo,
			TimeSpanWithRounding timeSheet,
			TimeSpanForCalc calculationTimeSheet) {
		
		super(timeSheet, calculationTimeSheet);
	}
}
