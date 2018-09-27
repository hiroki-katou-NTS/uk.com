package nts.uk.ctx.at.record.infra.entity.breakorgoout;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OutingTotalTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.WithinOutingTotalTime;
import nts.uk.ctx.at.record.dom.stamp.GoOutReason;
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author keisuke_hoshina
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_OUTING_TIME")
public class KrcdtDayOutingTime extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtDayOutingTimePK krcdtDayOutingTimePK;

//	計上用合計時間
	@Column(name = "TO_RECORED_TOTAL_TIME")
	public int toRecoredTotalTime;
	//計上用所定外時間
	@Column(name = "TO_RECORED_OUT_TIME")
	public int toRecoredOutTime;
	//計上用所定内時間
	@Column(name = "TO_RECORED_IN_TIME")
	public int toRecoredInTime;
	//計上用所定内コア内時間
	@Column(name = "TO_RECORED_CORE_IN_TIME")
	public int toRecoredCoreInTime;
	//計上用所定内コア外時間
	@Column(name = "TO_RECORED_CORE_OUT_TIME")
	public int toRecoredCoreOutTime;
	
	//控除用合計時間
	@Column(name = "DEDUCTION_TOTAL_TIME")
	public int deductionTotalTime;
	//控除用所定外時間
	@Column(name = "DEDUCTION_OUT_TIME")
	public int deductionOutTime;
	//控除用所定内時間
	@Column(name = "DEDUCTION_IN_TIME")
	public int deductionInTime;
	//控除用所定内コア内時間
	@Column(name = "DEDUCTION_CORE_IN_TIME")
	public int deductionCoreInTime;
	//控除用所定内コア外時間
	@Column(name = "DEDUCTION_CORE_OUT_TIME")
	public int deductionCoreOutTime;
	
	//計算計上用合計時間
	@Column(name = "CAL_TO_RECORED_TOTAL_TIME")
	public int calToRecoredTotalTime;
	//計算計上用所定外時間
	@Column(name = "CAL_TO_RECORED_OUT_TIME")
	public int calToRecoredOutTime;
	//計算計上用所定内時間
	@Column(name = "CAL_TO_RECORED_IN_TIME")
	public int calToRecoredInTime;
	//計算計上用所定内コア内時間
	@Column(name = "CAL_TO_RECORED_CORE_IN_TIME")
	public int calToRecoredCoreInTime;
	//計算計上用所定内コア外時間
	@Column(name = "CAL_TO_RECORED_CORE_OUT_TIME")
	public int calToRecoredCoreOutTime;
	
	//計算控除用合計時間
	@Column(name = "CAL_DEDUCTION_TOTAL_TIME")
	public int calDeductionTotalTime;
	//計算控除用所定外時間
	@Column(name = "CAL_DEDUCTION_OUT_TIME")
	public int calDeductionOutTime;
	//計算控除用所定内時間
	@Column(name = "CAL_DEDUCTION_IN_TIME")
	public int calDeductionInTime;
	//計算控除用所定内コア内時間
	@Column(name = "CAL_DEDUCTION_CORE_IN_TIME")
	public int calDeductionCoreInTime;
	//計算控除用所定内コア外時間
	@Column(name = "CAL_DEDUCTION_CORE_OUT_TIME")
	public int calDeductionCoreOutTime;
	
	//時間年休使用時間
	@Column(name = "ANUUUAL_LEAVE_USE_TIME")
	public int anuuualLeaveUseTime;
	//時間代休使用時間
	@Column(name = "COMPENS_LEAVE_USE_TIME")
	public int compensLeaveUseTime;
	//特別休暇使用時間
	@Column(name = "SPECIAL_HOLIDAY_USE_TIME")
	public int specialHolidayUseTime;
	//超過有休使用時間
	@Column(name = "OVER_VACATION_USE_TIME")
	public int overVacationUseTime;
	//外出回数
	@Column(name = "OUTING_COUNT")
	public int count;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = {
			@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDayTime krcdtDayTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayOutingTimePK;
	}
	
	
	public static KrcdtDayOutingTime toEntity(String empId, GeneralDate ymd, OutingTimeOfDaily outingTimeOfDaily) {
		val entity = new KrcdtDayOutingTime();
		entity.krcdtDayOutingTimePK = new KrcdtDayOutingTimePK(empId,ymd,outingTimeOfDaily.getReason().value);
		entity.setData(outingTimeOfDaily);
		return entity;
	}


	public void setData(OutingTimeOfDaily outingTimeOfDaily)
	{
		this.toRecoredTotalTime = 0 ;
		this.toRecoredOutTime = 0 ;
		this.toRecoredInTime = 0 ;
		this.toRecoredCoreInTime = 0 ;
		this.toRecoredCoreOutTime = 0 ;
		this.deductionTotalTime = 0 ;
		this.deductionOutTime = 0 ;
		this.deductionInTime = 0 ;
		this.deductionCoreInTime = 0 ;
		this.deductionCoreOutTime = 0 ;
		this.calToRecoredTotalTime = 0 ;
		this.calToRecoredOutTime = 0 ;
		this.calToRecoredInTime = 0 ;
		this.calToRecoredCoreInTime = 0 ;
		this.calToRecoredCoreOutTime = 0 ;
		this.calDeductionTotalTime = 0 ;
		this.calDeductionOutTime = 0 ;
		this.calDeductionInTime = 0 ;
		this.calDeductionCoreInTime = 0 ;
		this.calDeductionCoreOutTime = 0 ;
		this.anuuualLeaveUseTime = 0 ;
		this.compensLeaveUseTime = 0 ;
		this.specialHolidayUseTime = 0 ;
		this.overVacationUseTime = 0 ;
		this.count = 0 ;
		
		if(outingTimeOfDaily != null) {
			
			
			this.toRecoredTotalTime = outingTimeOfDaily.getRecordTotalTime().getTotalTime().getTime().valueAsMinutes();
			this.toRecoredOutTime = outingTimeOfDaily.getRecordTotalTime().getExcessTotalTime().getTime().valueAsMinutes() ;
			this.toRecoredInTime = outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getTotalTime().getTime().valueAsMinutes() ;
			this.toRecoredCoreInTime = outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getWithinCoreTime().getTime().valueAsMinutes() ;
			this.toRecoredCoreOutTime = outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getExcessCoreTime().getTime().valueAsMinutes() ;
			
			this.calToRecoredTotalTime =  outingTimeOfDaily.getRecordTotalTime().getTotalTime().getCalcTime().valueAsMinutes();
			this.calToRecoredOutTime =  outingTimeOfDaily.getRecordTotalTime().getExcessTotalTime().getCalcTime().valueAsMinutes();
			this.calToRecoredInTime =  outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getTotalTime().getCalcTime().valueAsMinutes();
			this.calToRecoredCoreInTime = outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getWithinCoreTime().getCalcTime().valueAsMinutes() ;
			this.calToRecoredCoreOutTime = outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getExcessCoreTime().getCalcTime().valueAsMinutes() ;
			
			this.deductionTotalTime = outingTimeOfDaily.getDeductionTotalTime().getTotalTime().getTime().valueAsMinutes() ;
			this.deductionOutTime = outingTimeOfDaily.getDeductionTotalTime().getExcessTotalTime().getTime().valueAsMinutes() ;
			this.deductionInTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getTotalTime().getTime().valueAsMinutes() ;
			this.deductionCoreInTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getWithinCoreTime().getTime().valueAsMinutes() ;
			this.deductionCoreOutTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getExcessCoreTime().getTime().valueAsMinutes() ;
			
			this.calDeductionTotalTime = outingTimeOfDaily.getDeductionTotalTime().getTotalTime().getCalcTime().valueAsMinutes();
			this.calDeductionOutTime = outingTimeOfDaily.getDeductionTotalTime().getExcessTotalTime().getCalcTime().valueAsMinutes() ;
			this.calDeductionInTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getTotalTime().getCalcTime().valueAsMinutes() ;
			this.calDeductionCoreInTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getWithinCoreTime().getCalcTime().valueAsMinutes() ;
			this.calDeductionCoreOutTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getExcessCoreTime().getCalcTime().valueAsMinutes() ;
			
			this.anuuualLeaveUseTime = outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime().valueAsMinutes() ;
			this.compensLeaveUseTime = outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime().valueAsMinutes() ;
			this.specialHolidayUseTime = outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime().valueAsMinutes() ;
			this.overVacationUseTime =  outingTimeOfDaily.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime().valueAsMinutes() ;
			
			this.count = outingTimeOfDaily.getWorkTime().v();
		}
	}
	
	public OutingTimeOfDaily toDomain() {
		val reason = GoOutReason.corvert(this.krcdtDayOutingTimePK.reason);
		return new OutingTimeOfDaily(new BreakTimeGoOutTimes(count),
									 //外出理由
									 reason.isPresent()?reason.get():GoOutReason.OFFICAL, 
									 //休暇使用時間
									 new TimevacationUseTimeOfDaily(new AttendanceTime(anuuualLeaveUseTime), 
											 						new AttendanceTime(compensLeaveUseTime), 
											 						new AttendanceTime(overVacationUseTime), 
											 						new AttendanceTime(specialHolidayUseTime)), 
									 //計上外出合計時間
									 OutingTotalTime.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(toRecoredTotalTime), new AttendanceTime(calToRecoredTotalTime)),
											 			WithinOutingTotalTime.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(toRecoredInTime), new AttendanceTime(calToRecoredInTime)), 
											 									 TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(toRecoredCoreInTime), new AttendanceTime(calToRecoredCoreInTime)),
											 									 TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(toRecoredCoreOutTime), new AttendanceTime(calToRecoredCoreOutTime))), 
											 			TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(toRecoredOutTime), new AttendanceTime(calToRecoredOutTime))),
									 //控除外出合計時間
									 OutingTotalTime.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(deductionTotalTime), new AttendanceTime(calDeductionTotalTime)),
									 					WithinOutingTotalTime.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(deductionInTime), new AttendanceTime(calDeductionInTime)), 
									 											 TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(deductionCoreInTime), new AttendanceTime(calDeductionCoreInTime)),
									 											 TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(deductionCoreOutTime), new AttendanceTime(calDeductionCoreOutTime))), 
									 					TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(calDeductionOutTime), new AttendanceTime(calDeductionOutTime))),
									 //補正後時間帯
									 new ArrayList<>());
	}
}
