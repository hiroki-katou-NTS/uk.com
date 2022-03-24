package nts.uk.ctx.at.record.infra.entity.daily.leaveearlytime; 

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.val;
import nts.arc.time.GeneralDate;
//import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTimeAtd;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_TIME_LEAVEEARLY")
public class KrcdtDayLeaveEarlyTime  extends ContractUkJpaEntity implements Serializable{
	
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
	/*特別休暇枠NO*/
	@Column(name = "SPHD_NO")
	public Integer specialHdFrameNo;
	/*子の看護休暇使用時間*/
	@Column(name = "CHILD_CARE_USE_TIME")
	public int childCareUseTime;
	/*介護休暇使用時間*/
	@Column(name = "CARE_USE_TIME")
	public int careUseTime;

	/** 時間年休相殺時間 */
	@Column(name = "TIME_ANALLV_OFFSET_TIME")
	public int anuuualLeaveOffTime;
	/** 時間代休相殺時間 */
	@Column(name = "TIME_CMPNSTLV_OFFSET_TIME")
	public int compensLeaveOffTime;
	/** 超過有休相殺時間 */
	@Column(name = "OVER_PAY_VACTN_OFFSET_TIME")
	public int specialHolidayOffTime;
	/** 特別休暇相殺時間 */
	@Column(name = "SP_VACTN_OFFSET_TIME")
	public int overVacationOffTime;
	/** 子の看護休暇相殺時間 */
	@Column(name = "CHILD_CARE_OFFSET_TIME")
	public int childCareOffTime;
	/** 介護休暇相殺時間 */
	@Column(name = "CARE_OFFSET_TIME")
	public int careOffTime;
	/** 加算時間 */
	@Column(name = "ADD_TIME")
	public int addTime;
	
//	@ManyToOne
//	@JoinColumns(value = { 
//			@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
//			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false)
//	})
//	public KrcdtDayAttendanceTime krcdtDayAttendanceTime;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = { 
			@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false)
	})
	public KrcdtDayTimeAtd krcdtDayTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayLeaveEarlyTimePK;
	}
	
	public static KrcdtDayLeaveEarlyTime create(String employeeId, GeneralDate ymd, LeaveEarlyTimeOfDaily leaveEarlyTime) {
		val entity = new KrcdtDayLeaveEarlyTime();
		entity.krcdtDayLeaveEarlyTimePK = new KrcdtDayLeaveEarlyTimePK(employeeId, ymd, leaveEarlyTime.getWorkNo().v().intValue());
		entity.setData(leaveEarlyTime);
		return entity;
	}
	
	public void setData(LeaveEarlyTimeOfDaily leaveEarlyTime){
		if(leaveEarlyTime == null){
			return;
		}
		if(leaveEarlyTime.getLeaveEarlyTime() != null){
			TimeWithCalculation leaveEarly = leaveEarlyTime.getLeaveEarlyTime();
			//早退時間
			this.leaveEarlyTime = leaveEarly.getTime() == null ? 0 : leaveEarly.getTime().valueAsMinutes();
			this.calcLeaveEarlyTime = leaveEarly.getCalcTime() == null ? 0 : leaveEarly.getCalcTime().valueAsMinutes();
		}
		if(leaveEarlyTime.getLeaveEarlyDeductionTime() != null){
			TimeWithCalculation leaveEarlyDedct = leaveEarlyTime.getLeaveEarlyDeductionTime();
			//早退控除時間
			this.leaveEarlyDedctTime = leaveEarlyDedct.getTime() == null ? 0 : leaveEarlyDedct.getTime().valueAsMinutes();
			this.calcLeaveEarlyDedctTime = leaveEarlyDedct.getCalcTime() == null ? 0 : leaveEarlyDedct.getCalcTime().valueAsMinutes();
		}
		//休暇使用時間
		if(leaveEarlyTime.getTimePaidUseTime() != null){
			TimevacationUseTimeOfDaily vacationUse = leaveEarlyTime.getTimePaidUseTime();
			//年休
			this.timeAnallvUseTime = vacationUse.getTimeAnnualLeaveUseTime() == null ? 0 : vacationUse.getTimeAnnualLeaveUseTime().valueAsMinutes();
			//代休
			this.timeCmpnstlvUseTime = vacationUse.getTimeCompensatoryLeaveUseTime() == null ? 0 : vacationUse.getTimeCompensatoryLeaveUseTime().valueAsMinutes();
			//超過
			this.overPayVactnUseTime = vacationUse.getSixtyHourExcessHolidayUseTime() == null ? 0 : vacationUse.getSixtyHourExcessHolidayUseTime().valueAsMinutes();
			//特別休暇
			this.spVactnUseTime = vacationUse.getTimeSpecialHolidayUseTime() == null ? 0 : vacationUse.getTimeSpecialHolidayUseTime().valueAsMinutes();
			/*特別休暇枠No*/
			this.specialHdFrameNo = vacationUse.getSpecialHolidayFrameNo().map(c -> c.v()).orElse(null);
			/*子の看護休暇使用時間*/
			this.childCareUseTime = vacationUse.getTimeChildCareHolidayUseTime() == null ? 0 : vacationUse.getTimeChildCareHolidayUseTime().valueAsMinutes();
			/*介護休暇使用時間*/
			this.careUseTime = vacationUse.getTimeCareHolidayUseTime() == null ? 0 : vacationUse.getTimeCareHolidayUseTime().valueAsMinutes();
		}

		/** 時間年休相殺時間 */
		this.anuuualLeaveOffTime = leaveEarlyTime.getTimeOffsetUseTime().getTimeAnnualLeaveUseTime().valueAsMinutes();
		/** 時間代休相殺時間 */
		this.compensLeaveOffTime = leaveEarlyTime.getTimeOffsetUseTime().getTimeCompensatoryLeaveUseTime().valueAsMinutes();
		/** 超過有休相殺時間 */
		this.specialHolidayOffTime = leaveEarlyTime.getTimeOffsetUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes();
		/** 特別休暇相殺時間 */
		this.overVacationOffTime = leaveEarlyTime.getTimeOffsetUseTime().getSixtyHourExcessHolidayUseTime().valueAsMinutes();
		/** 子の看護休暇相殺時間 */
		this.childCareOffTime = leaveEarlyTime.getTimeOffsetUseTime().getTimeChildCareHolidayUseTime().valueAsMinutes();
		/** 介護休暇相殺時間 */
		this.careOffTime = leaveEarlyTime.getTimeOffsetUseTime().getTimeCareHolidayUseTime().valueAsMinutes();
		/** 加算時間 */
		this.addTime = leaveEarlyTime.getAddTime().valueAsMinutes();
	}
	
	
	
	public LeaveEarlyTimeOfDaily toDomain() {
		TimevacationUseTimeOfDaily timeVacation = new TimevacationUseTimeOfDaily(
				new AttendanceTime(this.timeAnallvUseTime),
				new AttendanceTime(this.timeCmpnstlvUseTime),
				new AttendanceTime(this.overPayVactnUseTime),
				new AttendanceTime(this.spVactnUseTime),
				Optional.ofNullable(this.specialHdFrameNo == null ? null : new SpecialHdFrameNo(this.specialHdFrameNo)),
				new AttendanceTime(this.childCareUseTime),
				new AttendanceTime(this.careUseTime));
		
		return new LeaveEarlyTimeOfDaily(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.leaveEarlyTime), new AttendanceTime(this.calcLeaveEarlyTime)),
										 TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.leaveEarlyDedctTime), new AttendanceTime(this.calcLeaveEarlyDedctTime)),
										 new WorkNo(this.krcdtDayLeaveEarlyTimePK == null ? null : this.krcdtDayLeaveEarlyTimePK.workNo),
										 timeVacation,
										 new IntervalExemptionTime(),
										 new TimevacationUseTimeOfDaily(
												new AttendanceTime(this.anuuualLeaveOffTime),
												new AttendanceTime(this.compensLeaveOffTime),
												new AttendanceTime(this.overVacationOffTime),
												new AttendanceTime(this.specialHolidayOffTime),
												Optional.ofNullable(this.specialHdFrameNo == null ? null : new SpecialHdFrameNo(this.specialHdFrameNo)),
												new AttendanceTime(this.childCareOffTime),
												new AttendanceTime(this.careOffTime)),
										 new AttendanceTime(this.addTime));
	}
}
