package nts.uk.ctx.at.record.infra.entity.daily.leaveearlytime; 

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.latetime.IntervalExemptionTime;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_LEAVEEARLYTIME")
public class KrcdtDayLeaveEarlyTime  extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayLeaveEarlyTimePK krcdtDayLeaveEarlyTimePK;
	
	/*早退時間*/
	@Column(name = "LEAVEEARLY_TIME")
	public int leaveEarlyTime;
	/*計算早退時間*/
	@Column(name = "CALC_LEAVEEARLY_TIME")
	public int calcLeaveEarlyTime;
	/*早退控除時間*/
	@Column(name = "LEAVEEARLY_DEDCT_TIME")
	public int leaveEarlyDedctTime;
	/*計算早退控除時間*/
	@Column(name = "CALC_LEAVEEARLY_DEDCT_TIME")
	public int calcLeaveEarlyDedctTime;
	/*時間年休使用時間*/
	@Column(name = "TIME_ANALLV_USE_TIME")
	public int timeAnallvUseTime;
	/*時間代休使用時間*/
	@Column(name = "TIME_CMPNSTLV_USE_TIME")
	public int timeCmpnstlvUseTime;
	/*超過有休使用時間*/
	@Column(name = "OVER_PAY_VACTN_USE_TIME")
	public int overPayVactnUseTime;
	/*特別休暇使用時間*/
	@Column(name = "SP_VACTN_USE_TIME")
	public int spVactnUseTime;
	
	@ManyToOne
	@JoinColumns(value = { 
			@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false)
	})
	public KrcdtDayAttendanceTime krcdtDayAttendanceTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayLeaveEarlyTimePK;
	}
	
	public static KrcdtDayLeaveEarlyTime create(String employeeId, GeneralDate ymd, LeaveEarlyTimeOfDaily leaveEarlyTime) {
		val entity = new KrcdtDayLeaveEarlyTime();
		entity.krcdtDayLeaveEarlyTimePK = new KrcdtDayLeaveEarlyTimePK(employeeId, ymd, leaveEarlyTime.getWorkNo().v().intValue());
		//早退時間
		entity.leaveEarlyTime = leaveEarlyTime.getLeaveEarlyTime().getTime().valueAsMinutes();
		entity.calcLeaveEarlyTime = leaveEarlyTime.getLeaveEarlyTime().getCalcTime().valueAsMinutes();
		//早退控除時間
		entity.leaveEarlyDedctTime = leaveEarlyTime.getLeaveEarlyDeductionTime().getTime().valueAsMinutes();
		entity.calcLeaveEarlyDedctTime = leaveEarlyTime.getLeaveEarlyDeductionTime().getCalcTime().valueAsMinutes();
		//休暇使用時間
		//年休
		entity.timeAnallvUseTime = leaveEarlyTime.getTimePaidUseTime().getTimeAnnualLeaveUseTime().valueAsMinutes();
		//代休
		entity.timeCmpnstlvUseTime = leaveEarlyTime.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().valueAsMinutes();
		//超過
		entity.overPayVactnUseTime = leaveEarlyTime.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().valueAsMinutes();
		//特別休暇
		entity.spVactnUseTime = leaveEarlyTime.getTimePaidUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes();
		return entity;
	}
	
	
	
	public LeaveEarlyTimeOfDaily toDomain() {
		TimevacationUseTimeOfDaily timeVacation = new TimevacationUseTimeOfDaily(new AttendanceTime(this.timeAnallvUseTime),
																				 new AttendanceTime(this.timeCmpnstlvUseTime),
																				 new AttendanceTime(this.overPayVactnUseTime),
																				 new AttendanceTime(this.spVactnUseTime));
		
		return new LeaveEarlyTimeOfDaily(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.leaveEarlyTime), new AttendanceTime(this.calcLeaveEarlyTime)),
										 TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.leaveEarlyDedctTime), new AttendanceTime(this.calcLeaveEarlyDedctTime)),
										 new WorkNo(this.krcdtDayLeaveEarlyTimePK == null ? null : this.krcdtDayLeaveEarlyTimePK.workNo),
										 timeVacation,
										 new IntervalExemptionTime(new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0)));
	}
}
