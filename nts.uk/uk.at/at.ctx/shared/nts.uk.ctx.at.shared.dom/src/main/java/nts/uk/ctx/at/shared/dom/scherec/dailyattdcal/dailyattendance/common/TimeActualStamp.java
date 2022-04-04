package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author nampt
 * 勤怠打刻(実打刻付き)
 *
 */
@Getter
public class TimeActualStamp {
	//実打刻
	@Setter
	private Optional<WorkStamp> actualStamp = Optional.empty();
	//打刻
	@Setter
	private Optional<WorkStamp> stamp = Optional.empty();
	
	//打刻反映回数
	@Setter
	private Integer numberOfReflectionStamp;
	
	//時間外の申告
	@Setter
	private Optional<OvertimeDeclaration> overtimeDeclaration = Optional.empty();
	
	//時間休暇時間帯
	@Setter
	private Optional<TimeSpanForCalc> timeVacation = Optional.empty();
	
	/**
	 * 打刻時間を指定時間分経過させた勤怠打刻を返す
	 * @param moveTime 指定時間
	 * @return 勤怠打刻
	 */
	public TimeActualStamp moveAheadStampTime(int moveTime) {
		WorkStamp actualWorkStamp = new WorkStamp(
				this.actualStamp.isPresent() && this.actualStamp.get().getTimeDay().getTimeWithDay() != null
						? (this.actualStamp.get().getTimeDay().getTimeWithDay().isPresent()
								? this.actualStamp.get().getTimeDay().getTimeWithDay().get().forwardByMinutes(moveTime)
								: null)
						: null,
				this.actualStamp.isPresent() && this.actualStamp.get().getLocationCode().isPresent()
						? this.actualStamp.get().getLocationCode().get()
						: null,
				this.actualStamp.isPresent() ? this.actualStamp.get().getTimeDay().getReasonTimeChange().getTimeChangeMeans()
						: TimeChangeMeans.AUTOMATIC_SET);

		WorkStamp stamp = new WorkStamp(
				this.stamp.isPresent() && this.stamp.get().getTimeDay().getTimeWithDay() != null
						? (this.stamp.get().getTimeDay().getTimeWithDay().isPresent()?this.stamp.get().getTimeDay().getTimeWithDay().get().forwardByMinutes(moveTime):null)
						: null,
				this.stamp.isPresent() && this.stamp.get().getLocationCode().isPresent()
						? this.stamp.get().getLocationCode().get()
						: null,
				this.stamp.isPresent() ? this.stamp.get().getTimeDay().getReasonTimeChange().getTimeChangeMeans() : TimeChangeMeans.AUTOMATIC_SET);
		
		return new TimeActualStamp( actualWorkStamp,
									stamp,
									this.numberOfReflectionStamp);
	}
	/**
	 * 打刻時間を指定時間分戻した勤怠打刻を返す
	 * @param moveTime 指定時間
	 * @return 勤怠打刻
	 */
	public TimeActualStamp moveBackStampTime(int moveTime) {
		WorkStamp actualWorkStamp = new WorkStamp(
				this.actualStamp.isPresent() && this.actualStamp.get().getTimeDay().getTimeWithDay() != null
						? (this.actualStamp.get().getTimeDay().getTimeWithDay().isPresent()?this.actualStamp.get().getTimeDay().getTimeWithDay().get().backByMinutes(moveTime):null)
						: null,
				this.actualStamp.isPresent() && this.actualStamp.get().getLocationCode().isPresent()
						? this.actualStamp.get().getLocationCode().get()
						: null,
				this.actualStamp.isPresent()
						? this.actualStamp.get().getTimeDay().getReasonTimeChange().getTimeChangeMeans()
						: TimeChangeMeans.AUTOMATIC_SET);

		WorkStamp stamp = new WorkStamp(
				this.stamp.isPresent() && this.stamp.get().getTimeDay().getTimeWithDay() != null
						? (this.stamp.get().getTimeDay().getTimeWithDay().isPresent()
								? this.stamp.get().getTimeDay().getTimeWithDay().get().backByMinutes(moveTime)
								: null)
						: null,
				this.stamp.isPresent() && this.stamp.get().getLocationCode().isPresent()
						? this.stamp.get().getLocationCode().get()
						: null,
				this.stamp.isPresent() ? this.stamp.get().getTimeDay().getReasonTimeChange().getTimeChangeMeans()
						: TimeChangeMeans.AUTOMATIC_SET);
		return new TimeActualStamp(actualWorkStamp,
								   stamp,
                				   this.numberOfReflectionStamp);
	}
	public TimeActualStamp() {
		super();
		this.actualStamp = Optional.empty();
		this.stamp = Optional.empty();
		this.numberOfReflectionStamp = 0;
	}
	public TimeActualStamp(WorkStamp actualStamp, WorkStamp stamp, Integer numberOfReflectionStamp) {
		super();
		this.actualStamp = Optional.ofNullable(actualStamp);
		this.stamp = Optional.ofNullable(stamp);
		this.numberOfReflectionStamp = numberOfReflectionStamp;
	}
	public void setPropertyTimeActualStamp(Optional<WorkStamp> actualStamp, Optional<WorkStamp> stamp, Integer numberOfReflectionStamp){
		this.actualStamp = actualStamp == null ? Optional.empty() : actualStamp;
		this.stamp = stamp == null ? Optional.empty() : stamp;
		this.numberOfReflectionStamp = numberOfReflectionStamp;
	}
	
	/** 「打刻」を削除する */
	public void removeStamp() {
		this.stamp = Optional.empty();
	}
	
	public void setStampFromPcLogOn(TimeWithDayAttr pcLogOnStamp, GoLeavingWorkAtr goWork) {
		if(this.stamp.isPresent()) {
			if(this.stamp.get().getTimeDay().getTimeWithDay().isPresent() && this.stamp.get().getTimeDay().getTimeWithDay().get().greaterThan(pcLogOnStamp) && goWork.isGO_WORK()) {
				this.stamp.get().setStampFromPcLogOn(pcLogOnStamp);	
			}
			else if(this.stamp.get().getTimeDay().getTimeWithDay().isPresent() && this.stamp.get().getTimeDay().getTimeWithDay().get().lessThan(pcLogOnStamp) && goWork.isLEAVING_WORK()) {
				this.stamp.get().setStampFromPcLogOn(pcLogOnStamp);	
			}
		}
		if(this.actualStamp.isPresent()) {
			if(this.actualStamp.get().getTimeDay().getTimeWithDay().isPresent() && this.actualStamp.get().getTimeDay().getTimeWithDay().get().greaterThan(pcLogOnStamp) && goWork.isGO_WORK()) {
				this.actualStamp.get().setStampFromPcLogOn(pcLogOnStamp);	
			}
			else if(this.actualStamp.get().getTimeDay().getTimeWithDay().isPresent() && this.actualStamp.get().getTimeDay().getTimeWithDay().get().lessThan(pcLogOnStamp) && goWork.isLEAVING_WORK()) {
				this.actualStamp.get().setStampFromPcLogOn(pcLogOnStamp);	
			}
		}
	}
	
	/**
	 * 打刻(Stamp)が計算できる状態であるか判定する
	 * (null になっていないか) 
	 * @return 計算できる状態である
	 */
	public boolean isCalcStampState() {
		if(this.getStamp() != null && this.getStamp().isPresent()) {
			return true;
		}
		return false;
	}
	public TimeActualStamp(WorkStamp actualStamp,WorkStamp stamp, Integer numberOfReflectionStamp,
			OvertimeDeclaration overtimeDeclaration, TimeSpanForCalc timeVacation) {
		super();
		this.actualStamp =  Optional.ofNullable(actualStamp);
		this.stamp = Optional.ofNullable(stamp);
		this.numberOfReflectionStamp = numberOfReflectionStamp;
		this.overtimeDeclaration = Optional.ofNullable(overtimeDeclaration);
		this.timeVacation = Optional.ofNullable(timeVacation);
	}
	
	
	
	/**
	 * @param actualStamp 実打刻
	 * @param stamp 打刻
	 * @param numberOfReflectionStamp 打刻反映回数
	 * @param overtimeDeclaration 時間外の申告
	 * @param timeVacation 時間休暇時間帯
	 */
	public TimeActualStamp(Optional<WorkStamp> actualStamp, Optional<WorkStamp> stamp, Integer numberOfReflectionStamp,
			Optional<OvertimeDeclaration> overtimeDeclaration, Optional<TimeSpanForCalc> timeVacation) {
		super();
		this.actualStamp = actualStamp;
		this.stamp = stamp;
		this.numberOfReflectionStamp = numberOfReflectionStamp;
		this.overtimeDeclaration = overtimeDeclaration;
		this.timeVacation = timeVacation;
	}
	
	/**
	 * 	[C-1] 自動セットで作る
	 * @param time 	時刻
	 * @return
	 */
	public static TimeActualStamp createByAutomaticSet(TimeWithDayAttr time) {
		
		return new TimeActualStamp(
				Optional.empty(), 
				Optional.of(WorkStamp.createByAutomaticSet(time)), 
				Integer.valueOf(1), 
				Optional.empty(), 
				Optional.empty());
	}
	
	public TimeActualStamp createByAutomaticSet() {
			
			return new TimeActualStamp(
					this.actualStamp, 
					this.stamp.map(x -> x.createByAutomaticSet()),
					this.numberOfReflectionStamp, 
					this.overtimeDeclaration, 
					this.timeVacation);
		}
	//時刻変更手段とデフォルトを作成する
	public static TimeActualStamp createDefaultWithReason(TimeChangeMeans reason) {
		return new TimeActualStamp(null, new WorkStamp(
				new WorkTimeInformation(new ReasonTimeChange(reason, Optional.empty()), null), Optional.empty()), 0);
	}
}
