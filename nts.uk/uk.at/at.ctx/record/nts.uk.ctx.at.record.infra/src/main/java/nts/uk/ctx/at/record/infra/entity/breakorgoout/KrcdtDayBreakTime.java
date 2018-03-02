package nts.uk.ctx.at.record.infra.entity.breakorgoout;

import java.io.Serializable;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTimePK;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author keisuke_hoshina
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_BREAK_TIME")
public class KrcdtDayBreakTime extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDayBreakTimePK krcdtDayBreakTimePK;
	
	@Column(name = "TO_RECORD_TOTAL_TIME")
	public Integer toRecordTotalTime;
	@Column(name ="TO_RECORD_IN_TIME")
	public Integer toRecordInTime;
	@Column(name ="TO_RECORD_OUT_TIME")
	public Integer toRecordOutTime;
	
	@Column(name ="DEDUCTION_TOTAL_TIME")
	public Integer deductionTotalTime;
	@Column(name ="DEDUCTION_IN_TIME")
	public Integer deductionInTime;
	@Column(name ="DEDUCTION_OUT_TIME")
	public Integer deductionOutTime;
	
	@Column(name ="CAL_TO_RECORD_TOTAL_TIME")
	public Integer calToRecordTotalTime;
	@Column(name ="CAL_TO_RECORD_IN_TIME")
	public Integer calToRecordInTime;
	@Column(name ="CAL_TO_RECORD_OUT_TIME")
	public Integer calToRecordOutTime;
	
	@Column(name ="CAL_DEDUCTION_TOTAL_TIME")
	public Integer calDeductionTotalTime;
	@Column(name ="CAL_DEDUCTION_IN_TIME")
	public Integer calDeductionInTime;
	@Column(name ="CAL_DEDUCTION_OUT_TIME")
	public Integer calDeductionOutTime;
	
	@Column(name ="DURINGWORK_TIME")
	public Integer duringworkTime;
	
	@Column(name ="COUNT")
	public Integer count;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayBreakTimePK;
	}
	
	/**
	 * Entityへの変換 
	 */
	public static KrcdtDayBreakTime toEntity(String employeeId, GeneralDate ymd,
			AttendanceTimeOfDailyPerformance attendanceTime) {
		val entity = new KrcdtDayBreakTime();
		entity.krcdtDayBreakTimePK = new KrcdtDayBreakTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd());
		entity.setData(attendanceTime);
		return entity;
	}

	/**
	 * PrimaryKey以外の値セット 
	 */
	public void setData(AttendanceTimeOfDailyPerformance attendanceTime) {
		val breakTimeDaily = attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getBreakTimeOfDaily();
		val recordTime = breakTimeDaily.getToRecordTotalTime();
		val dedTime = breakTimeDaily.getDeductionTotalTime();
		val duringTime = breakTimeDaily.getWorkTime();
		val workTimes = breakTimeDaily.getGooutTimes();
		if(!breakTimeDaily.equals(null)) {
			this.toRecordTotalTime = recordTime.getTotalTime() == null ? 0 : recordTime.getTotalTime().getTime().valueAsMinutes();
			this.calToRecordTotalTime = recordTime.getTotalTime() == null ? 0 : recordTime.getTotalTime().getCalcTime().valueAsMinutes();
			
			this.toRecordInTime = recordTime.getWithinStatutoryTotalTime() == null ? 0 : recordTime.getWithinStatutoryTotalTime().getTime().valueAsMinutes();
			this.calToRecordInTime = recordTime.getWithinStatutoryTotalTime() == null ? 0 : recordTime.getWithinStatutoryTotalTime().getCalcTime().valueAsMinutes();;
			
			this.toRecordOutTime = recordTime.getExcessOfStatutoryTotalTime() == null ? 0 : recordTime.getExcessOfStatutoryTotalTime().getTime().valueAsMinutes();
			this.calToRecordOutTime = recordTime.getExcessOfStatutoryTotalTime() == null ? 0 : recordTime.getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes();
			
			this.deductionTotalTime = dedTime.getTotalTime() == null ? 0 : dedTime.getTotalTime().getTime().valueAsMinutes();
			this.calDeductionTotalTime = dedTime.getTotalTime() == null ? 0 : dedTime.getTotalTime().getCalcTime().valueAsMinutes();
			
			this.deductionInTime = dedTime.getWithinStatutoryTotalTime() == null ? 0 : dedTime.getWithinStatutoryTotalTime().getTime().valueAsMinutes();
			this.calDeductionInTime = dedTime.getWithinStatutoryTotalTime() == null ? 0 : dedTime.getWithinStatutoryTotalTime().getCalcTime().valueAsMinutes();
			
			this.deductionOutTime = dedTime.getExcessOfStatutoryTotalTime() == null ? 0 : dedTime.getExcessOfStatutoryTotalTime().getTime().valueAsMinutes();;
			this.calDeductionOutTime = dedTime.getExcessOfStatutoryTotalTime() == null ? 0 : dedTime.getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes();;
			
			this.duringworkTime = duringTime == null ? 0 : duringTime.v();
			this.count = workTimes == null ? 0 : workTimes.v();
		}
	}
	
	/**
	 * ドメインへの変換
	 * @return 日別実績の休憩時間
	 */
	public BreakTimeOfDaily toDomain() {
		return new BreakTimeOfDaily(DeductionTotalTime.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.toRecordTotalTime), new AttendanceTime(this.calToRecordTotalTime)),
														  TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.toRecordInTime), new AttendanceTime(this.calToRecordInTime)),
														  TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.toRecordOutTime), new AttendanceTime(this.calToRecordOutTime))), 
									DeductionTotalTime.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.deductionTotalTime), new AttendanceTime(this.calDeductionTotalTime)),
											  			  TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.deductionInTime), new AttendanceTime(this.calDeductionInTime)),
											  			  TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.deductionOutTime), new AttendanceTime(this.calDeductionOutTime))), 
									new BreakTimeGoOutTimes(this.count), 
									new AttendanceTime(this.duringworkTime), 
									Collections.emptyList());
	}
}
