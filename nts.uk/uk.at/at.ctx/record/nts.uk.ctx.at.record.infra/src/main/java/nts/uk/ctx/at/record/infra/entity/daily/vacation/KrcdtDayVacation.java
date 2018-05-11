package nts.uk.ctx.at.record.infra.entity.daily.vacation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.AbsenceOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.OverSalaryOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.TimeDigestOfDaily;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.YearlyReservedOfDaily;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


@Entity
@Table(name = "KRCDT_DAY_VACATION")
public class KrcdtDayVacation extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KrcdtDayVacationPK krcdtDayVacationPK;
	/* 年休使用時間 */
	@Column(name = "ANNUALLEAVE_TIME")
	public int annualleaveTime;
	/* 年休時間消化休暇使用時間 */
	@Column(name = "ANNUALLEAVE_TDV_TIME")
	public int annualleaveTdvTime;
	/* 代休使用時間 */
	@Column(name = "COMPENSATORYLEAVE_TIME")
	public int compensatoryLeaveTime;
	/* 代休時間消化休暇使用時間 */
	@Column(name = "COMPENSATORYLEAVE_TDV_TIME")
	public int compensatoryLeaveTdvTime;
	/* 特別休暇使用時間 */
	@Column(name = "SPECIALHOLIDAY_TIME")
	public int specialHolidayTime;
	/* 特別休暇時間消化休暇使用時間 */
	@Column(name = "SPECIALHOLIDAY_TDV_TIME")
	public int specialHolidayTdvTime;
	/* 超過有休使用時間 */
	@Column(name = "EXCESSSALARIES_TIME")
	public int excessSalaryiesTime;
	/* 超過有休時間消化休暇使用時間*/
	@Column(name = "EXCESSSALARIES_TDV_TIME")
	public int excessSalaryiesTdvTime;
	/* 積立年休使用時間*/
	@Column(name = "RETENTIONYEARLY_TIME")
	public int retentionYearlyTime;
	/* 欠勤使用時間*/
	@Column(name = "ABSENCE_TIME")
	public int absenceTime;
	/* 時間消化休暇使用時間*/
	@Column(name = "TDV_TIME")
	public int tdvTime;
	/* 時間消化休暇不足時間*/
	@Column(name = "TDV_SHORTAGE_TIME")
	public int tdvShortageTime;
	
	@OneToOne(mappedBy="KrcdtDayVacation")
	public KrcdtDayAttendanceTime krcdtDayAttendanceTime;
	
	
	@Override
	protected Object getKey() {
		return this.krcdtDayVacationPK;
	}
	
	public HolidayOfDaily toDomain() {
		return new HolidayOfDaily(new AbsenceOfDaily(new AttendanceTime(absenceTime)),
								  new TimeDigestOfDaily(new AttendanceTime(tdvTime),new AttendanceTime(tdvShortageTime)),
								  new YearlyReservedOfDaily(new AttendanceTime(retentionYearlyTime)),
								  new SubstituteHolidayOfDaily(new AttendanceTime(compensatoryLeaveTime),new AttendanceTime(compensatoryLeaveTdvTime)),
								  new OverSalaryOfDaily(new AttendanceTime(excessSalaryiesTime),new AttendanceTime(excessSalaryiesTdvTime)),
								  new SpecialHolidayOfDaily(new AttendanceTime(specialHolidayTime),new AttendanceTime(specialHolidayTdvTime)),
								  new AnnualOfDaily(new AttendanceTime(annualleaveTime), new AttendanceTime(annualleaveTdvTime))
								 );
	}
	
	public static KrcdtDayVacation create(String employeeId, GeneralDate date, HolidayOfDaily domain) {
		val entity = new KrcdtDayVacation();
		
		entity.krcdtDayVacationPK = new KrcdtDayVacationPK(employeeId, date);
		entity.setData(domain);
		return entity;
	}
	
	public void setData(HolidayOfDaily domain) {
		if(domain == null)
			return;
		
		this.annualleaveTime = 0;
		this.annualleaveTdvTime = 0;
		this.compensatoryLeaveTime = 0;
		this.compensatoryLeaveTdvTime = 0;
		this.specialHolidayTime = 0;
		this.specialHolidayTdvTime = 0;
		this.excessSalaryiesTime = 0;
		this.excessSalaryiesTdvTime = 0;
		this.retentionYearlyTime = 0;
		this.absenceTime = 0;
		this.tdvTime = 0;
		this.tdvShortageTime = 0;
		//年休
		if(domain.getAnnual() != null) {
			this.annualleaveTime = domain.getAnnual() == null ? 0 : domain.getAnnual().getUseTime().valueAsMinutes();
			this.annualleaveTdvTime = domain.getAnnual() == null ? 0 : domain.getAnnual().getDigestionUseTime().valueAsMinutes();
		}

		//代休
		if(domain.getSubstitute() != null) {
			this.compensatoryLeaveTime = domain.getSubstitute() == null ? 0 : domain.getSubstitute().getUseTime().valueAsMinutes();
			this.compensatoryLeaveTdvTime = domain.getSubstitute() == null ? 0 : domain.getSubstitute().getDigestionUseTime().valueAsMinutes();
		}
		
		//特別休暇
		if(domain.getSpecialHoliday() != null) {
			this.specialHolidayTime = domain.getSpecialHoliday() == null ? 0 : domain.getSpecialHoliday().getUseTime().valueAsMinutes();
			this.specialHolidayTdvTime = domain.getSpecialHoliday() == null ? 0 : domain.getSpecialHoliday().getDigestionUseTime().valueAsMinutes();
		}
		//超過有休
		if(domain.getOverSalary() != null) {
			this.excessSalaryiesTime = domain.getOverSalary() == null ? 0 : domain.getOverSalary().getUseTime().valueAsMinutes();
			this.excessSalaryiesTdvTime = domain.getOverSalary() == null ? 0 : domain.getOverSalary().getDigestionUseTime().valueAsMinutes();
		}
		//積立
		if(domain.getYearlyReserved() != null) {
			this.retentionYearlyTime = domain.getYearlyReserved() == null ? 0 : domain.getYearlyReserved().getUseTime().valueAsMinutes(); 
		}
		//欠勤
		if(domain.getAbsence() != null) {
			this.absenceTime = domain.getAbsence() == null ? 0 : domain.getAbsence().getUseTime().valueAsMinutes(); 
		}
		//時間消化休暇
		if(domain.getTimeDigest() != null) {
			this.tdvTime = domain.getTimeDigest() == null ? 0 : domain.getTimeDigest().getUseTime().valueAsMinutes();
			this.tdvShortageTime = domain.getTimeDigest() == null ? 0 : domain.getTimeDigest().getLeakageTime().valueAsMinutes();;
		}
	}
}
