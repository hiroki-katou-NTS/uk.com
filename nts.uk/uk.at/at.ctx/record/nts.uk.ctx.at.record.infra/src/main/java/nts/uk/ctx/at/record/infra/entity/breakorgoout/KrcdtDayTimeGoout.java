package nts.uk.ctx.at.record.infra.entity.breakorgoout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

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
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTimeAtd;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.WithinOutingTotalTime;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author keisuke_hoshina
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_TIME_GOOUT")
public class KrcdtDayTimeGoout extends ContractUkJpaEntity implements Serializable{
	
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
	/*特別休暇枠NO*/
	@Column(name = "SPHD_NO")
	public Integer specialHdFrameNo;
	/*子の看護休暇使用時間*/
	@Column(name = "CHILD_CARE_USE_TIME")
	public int childCareUseTime;
	/*介護休暇使用時間*/
	@Column(name = "CARE_USE_TIME")
	public int careUseTime;	
	//外出回数
	@Column(name = "OUTING_COUNT")
	public int count;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = {
			@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDayTimeAtd krcdtDayTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayOutingTimePK;
	}
	
	
	public static KrcdtDayTimeGoout toEntity(String empId, GeneralDate ymd, OutingTimeOfDaily outingTimeOfDaily) {
		val entity = new KrcdtDayTimeGoout();
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
			
			if(outingTimeOfDaily.getRecordTotalTime() != null) {
				if(outingTimeOfDaily.getRecordTotalTime().getTotalTime() != null) {
					this.toRecoredTotalTime = outingTimeOfDaily.getRecordTotalTime().getTotalTime().getTime() != null ? outingTimeOfDaily.getRecordTotalTime().getTotalTime().getTime().valueAsMinutes(): 0 ;
					this.calToRecoredTotalTime =  outingTimeOfDaily.getRecordTotalTime().getTotalTime().getCalcTime() != null ? outingTimeOfDaily.getRecordTotalTime().getTotalTime().getCalcTime().valueAsMinutes(): 0 ; 
				}

				if(outingTimeOfDaily.getRecordTotalTime().getExcessTotalTime() != null) {
					this.toRecoredOutTime = outingTimeOfDaily.getRecordTotalTime().getExcessTotalTime().getTime() != null ? outingTimeOfDaily.getRecordTotalTime().getExcessTotalTime().getTime().valueAsMinutes() : 0;
					this.calToRecoredOutTime =  outingTimeOfDaily.getRecordTotalTime().getExcessTotalTime().getCalcTime() != null ? outingTimeOfDaily.getRecordTotalTime().getExcessTotalTime().getCalcTime().valueAsMinutes() : 0;
				}

				if(outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime() != null) {
					if(outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getTotalTime() != null) {
						this.toRecoredInTime = outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getTotalTime().getTime() != null ? outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getTotalTime().getTime().valueAsMinutes() : 0;
						this.calToRecoredInTime =  outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getTotalTime().getCalcTime() != null ? outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getTotalTime().getCalcTime().valueAsMinutes() : 0;
					}
					if(outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getWithinCoreTime() != null) {
						this.toRecoredCoreInTime = outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getWithinCoreTime().getTime() != null ? outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getWithinCoreTime().getTime().valueAsMinutes() : 0 ;
						this.calToRecoredCoreInTime = outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getWithinCoreTime().getCalcTime() != null ? outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getWithinCoreTime().getCalcTime().valueAsMinutes() : 0 ;
					}
					if(outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getExcessCoreTime() != null) {
						this.toRecoredCoreOutTime = outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getExcessCoreTime().getTime() != null ? outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getExcessCoreTime().getTime().valueAsMinutes() : 0 ;
						this.calToRecoredCoreOutTime = outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getExcessCoreTime().getCalcTime() != null ? outingTimeOfDaily.getRecordTotalTime().getWithinTotalTime().getExcessCoreTime().getCalcTime().valueAsMinutes() : 0 ;
					}
				}
			}
			if(outingTimeOfDaily.getDeductionTotalTime() != null) { 
				if(outingTimeOfDaily.getDeductionTotalTime().getTotalTime() != null) {
					this.deductionTotalTime = outingTimeOfDaily.getDeductionTotalTime().getTotalTime().getTime() != null ? outingTimeOfDaily.getDeductionTotalTime().getTotalTime().getTime().valueAsMinutes() : 0 ;
					this.calDeductionTotalTime = outingTimeOfDaily.getDeductionTotalTime().getTotalTime().getCalcTime()  != null ? outingTimeOfDaily.getDeductionTotalTime().getTotalTime().getCalcTime().valueAsMinutes() : 0 ;
				}
				if(outingTimeOfDaily.getDeductionTotalTime().getExcessTotalTime() != null) {
					this.deductionOutTime = outingTimeOfDaily.getDeductionTotalTime().getExcessTotalTime().getTime() != null ? outingTimeOfDaily.getDeductionTotalTime().getExcessTotalTime().getTime().valueAsMinutes() : 0 ;
					this.calDeductionOutTime = outingTimeOfDaily.getDeductionTotalTime().getExcessTotalTime().getCalcTime() != null ? outingTimeOfDaily.getDeductionTotalTime().getExcessTotalTime().getCalcTime().valueAsMinutes() : 0 ;
				}
				if(outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime() != null) {
					if(outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getTotalTime() != null) {
						this.deductionInTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getTotalTime().getTime() != null ? outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getTotalTime().getTime().valueAsMinutes() : 0 ;
						this.calDeductionInTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getTotalTime().getCalcTime() != null ? outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getTotalTime().getCalcTime().valueAsMinutes() : 0 ;
					}
					if(outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getWithinCoreTime() != null) {
						this.deductionCoreInTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getWithinCoreTime().getTime() != null ? outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getWithinCoreTime().getTime().valueAsMinutes() : 0 ;
						this.calDeductionCoreInTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getWithinCoreTime().getCalcTime() != null ? outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getWithinCoreTime().getCalcTime().valueAsMinutes() : 0 ;
					}
					if(outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getExcessCoreTime() != null) {
						this.deductionCoreOutTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getExcessCoreTime().getTime() != null ? outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getExcessCoreTime().getTime().valueAsMinutes() : 0;
						this.calDeductionCoreOutTime = outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getExcessCoreTime().getCalcTime() != null ? outingTimeOfDaily.getDeductionTotalTime().getWithinTotalTime().getExcessCoreTime().getCalcTime().valueAsMinutes() : 0 ;
					}
				}
			}
			if(outingTimeOfDaily.getTimeVacationUseOfDaily() != null) {
				this.anuuualLeaveUseTime = outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime() != null ? outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime().valueAsMinutes() : 0 ;
				this.compensLeaveUseTime = outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime() != null ? outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime().valueAsMinutes() : 0 ;
				this.specialHolidayUseTime = outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime() != null ? outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime().valueAsMinutes() : 0 ;
				this.overVacationUseTime =  outingTimeOfDaily.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime() != null ? outingTimeOfDaily.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime().valueAsMinutes() : 0 ;
				this.specialHdFrameNo = outingTimeOfDaily.getTimeVacationUseOfDaily().getSpecialHolidayFrameNo().map(c -> c.v()).orElse(null);
				this.childCareUseTime = outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeChildCareHolidayUseTime() != null ? outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeChildCareHolidayUseTime().valueAsMinutes() : 0;
				this.careUseTime = outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeCareHolidayUseTime() != null ? outingTimeOfDaily.getTimeVacationUseOfDaily().getTimeCareHolidayUseTime().valueAsMinutes() : 0;			
			
			}
			if(outingTimeOfDaily.getWorkTime() != null) {
				this.count = outingTimeOfDaily.getWorkTime().v();
			}
			
			/** 時間年休相殺時間 */
			this.anuuualLeaveOffTime = outingTimeOfDaily.getTimeOffsetUseTime().getTimeAnnualLeaveUseTime().valueAsMinutes();
			/** 時間代休相殺時間 */
			this.compensLeaveOffTime = outingTimeOfDaily.getTimeOffsetUseTime().getTimeCompensatoryLeaveUseTime().valueAsMinutes();
			/** 超過有休相殺時間 */
			this.specialHolidayOffTime = outingTimeOfDaily.getTimeOffsetUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes();
			/** 特別休暇相殺時間 */
			this.overVacationOffTime = outingTimeOfDaily.getTimeOffsetUseTime().getSixtyHourExcessHolidayUseTime().valueAsMinutes();
			/** 子の看護休暇相殺時間 */
			this.childCareOffTime = outingTimeOfDaily.getTimeOffsetUseTime().getTimeChildCareHolidayUseTime().valueAsMinutes();
			/** 介護休暇相殺時間 */
			this.careOffTime = outingTimeOfDaily.getTimeOffsetUseTime().getTimeCareHolidayUseTime().valueAsMinutes();
		}
	}
	
	public OutingTimeOfDaily toDomain() {
		val reason = GoingOutReason.corvert(this.krcdtDayOutingTimePK.reason);
		return new OutingTimeOfDaily(new BreakTimeGoOutTimes(count),
									 //外出理由
									 reason.isPresent()?reason.get():GoingOutReason.UNION, 
									 //休暇使用時間
									 new TimevacationUseTimeOfDaily(new AttendanceTime(anuuualLeaveUseTime), 
											 						new AttendanceTime(compensLeaveUseTime), 
											 						new AttendanceTime(overVacationUseTime), 
											 						new AttendanceTime(specialHolidayUseTime),
											 						Optional.ofNullable(this.specialHdFrameNo == null ? null : new SpecialHdFrameNo(this.specialHdFrameNo)),
											 						new AttendanceTime(this.childCareUseTime),
											 						new AttendanceTime(this.careUseTime)),
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
									 new ArrayList<>(),
									 new TimevacationUseTimeOfDaily(
												new AttendanceTime(this.anuuualLeaveOffTime),
												new AttendanceTime(this.compensLeaveOffTime),
												new AttendanceTime(this.overVacationOffTime),
												new AttendanceTime(this.specialHolidayOffTime),
												Optional.ofNullable(this.specialHdFrameNo == null ? null : new SpecialHdFrameNo(this.specialHdFrameNo)),
												new AttendanceTime(this.childCareOffTime),
												new AttendanceTime(this.careOffTime)));
	}
}
