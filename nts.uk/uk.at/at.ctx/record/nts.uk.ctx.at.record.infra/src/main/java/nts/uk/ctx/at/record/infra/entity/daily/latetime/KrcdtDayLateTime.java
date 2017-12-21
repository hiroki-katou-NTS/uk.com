package nts.uk.ctx.at.record.infra.entity.daily.latetime;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_LATETIME")
public class KrcdtDayLateTime extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayLateTimePK krcdtDayLateTimePK;
	
	/*遅刻時間*/
	@Column(name = "LATE_TIME")
	public int lateTime;
	/*計算遅刻時間*/
	@Column(name = "CALC_LATE_TIME")
	public int calcLateTime;
	/*遅刻控除時間*/
	@Column(name = "LATE_DEDCT_TIME")
	public int lateDedctTime;
	/*計算遅刻控除時間*/
	@Column(name = "CALC_LATE_DEDCT_TIME")
	public int calcLateDedctTime;
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
	
	@Override
	protected Object getKey() {
		return this.krcdtDayLateTimePK;
	}
	
	
	public static KrcdtDayLateTime create(String employeeId, GeneralDate date, LateTimeOfDaily domain) {
		val entity = new KrcdtDayLateTime();
		/*主キー*/
		entity.krcdtDayLateTimePK = new KrcdtDayLateTimePK(employeeId,date,domain.getWorkNo().v().intValue());
		/*遅刻時間*/
		entity.lateTime = domain.getLateTime().getTime().valueAsMinutes();
		/*計算遅刻時間*/
		entity.calcLateTime = domain.getLateTime().getCalcTime().valueAsMinutes();
		/*遅刻控除時間*/
		entity.lateDedctTime = domain.getLateDeductionTime().getTime().valueAsMinutes();
		/*計算遅刻控除時間*/
		entity.calcLateDedctTime = domain.getLateDeductionTime().getCalcTime().valueAsMinutes();
		/*時間年休使用時間*/
		entity.timeAnallvUseTime = domain.getTimePaidUseTime().getTimeAnnualLeaveUseTime().valueAsMinutes();
		/*時間代休使用時間*/
		entity.timeCmpnstlvUseTime = domain.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime().valueAsMinutes();
		/*超過有休使用時間*/
		entity.overPayVactnUseTime = domain.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime().valueAsMinutes();
		/*特別休暇使用時間*/
		entity.spVactnUseTime = domain.getTimePaidUseTime().getTimeSpecialHolidayUseTime().valueAsMinutes();
		return entity;
	}
	
	public LateTimeOfDaily toDomain() {
		return new LateTimeOfDaily(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.lateTime),new AttendanceTime(this.calcLateTime)),
								   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.lateDedctTime),new AttendanceTime(this.calcLateDedctTime)),
								   new WorkNo(BigDecimal.valueOf(this.krcdtDayLateTimePK.workNo)),
								   new TimevacationUseTimeOfDaily(new AttendanceTime(this.timeAnallvUseTime),
										   						  new AttendanceTime(this.timeCmpnstlvUseTime),
										   						  new AttendanceTime(this.overPayVactnUseTime),
										   						  new AttendanceTime(this.spVactnUseTime)));
	}
	
}
