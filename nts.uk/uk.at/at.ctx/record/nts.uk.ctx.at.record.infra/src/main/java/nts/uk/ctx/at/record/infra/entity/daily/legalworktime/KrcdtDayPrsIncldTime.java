package nts.uk.ctx.at.record.infra.entity.daily.legalworktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_PRS_INCLD_TIME")
public class KrcdtDayPrsIncldTime extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayPrsIncldTimePK krcdtDayPrsIncldTimePK;
	/*就業時間*/
	@Column(name = "WORK_TIME")
	public int workTime;
	/*実働就業時間*/
	@Column(name = "PEFOM_WORK_TIME")
	public int actWorkTime;
	/*所定内割増時間*/
	@Column(name = "PRS_INCLD_PRMIM_TIME")
	public int prsIncldPrmimTime;
	/*所定内深夜時間*/
	@Column(name = "PRS_INCLD_MIDN_TIME")
	public int prsIncldMidnTime;
	/*休暇加算時間*/
	@Column(name = "VACTN_ADD_TIME")
	public int vactnAddTime;
	/*所定内深夜乖離時間*/
	@Column(name = "DIV_PRS_INCLD_MIDN_TIME")
	public int divPrsIncldMidnTime;
	
	
	@OneToOne(mappedBy="krcdtDayPrsIncldTime")
	public KrcdtDayAttendanceTime krcdtDayAttendanceTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayPrsIncldTimePK;
	}
	
	
	public static KrcdtDayPrsIncldTime create(String employeeId, GeneralDate date, WithinStatutoryTimeOfDaily domain) {
		val entity = new KrcdtDayPrsIncldTime();
		/*主キー*/
		entity.krcdtDayPrsIncldTimePK = new KrcdtDayPrsIncldTimePK(employeeId, date);
		entity.setData(domain);
		return entity;
	}
	
	public void setData(WithinStatutoryTimeOfDaily domain){
		if(domain != null){
			/*就業時間*/
			this.workTime = domain.getWorkTime() == null ? 0 : domain.getWorkTime().valueAsMinutes();
			/*実働就業時間*/
//			this.actWorkTime = domain.getWorkTimeIncludeVacationTime() == null ? 0 : domain.getWorkTimeIncludeVacationTime().valueAsMinutes();
			this.actWorkTime = domain.getActualWorkTime() == null ? 0 : domain.getActualWorkTime().valueAsMinutes();
			/*所定内割増時間*/
			this.prsIncldPrmimTime = domain.getWithinPrescribedPremiumTime() == null ? 0 : domain.getWithinPrescribedPremiumTime().valueAsMinutes();
			if(domain.getWithinStatutoryMidNightTime() != null){
				TimeDivergenceWithCalculation winthinTime = domain.getWithinStatutoryMidNightTime().getTime();
				/*所定内深夜時間*/
				this.prsIncldMidnTime = winthinTime == null || winthinTime.getCalcTime() == null ? 0 
						: domain.getWithinStatutoryMidNightTime().getTime().getTime().valueAsMinutes();
				/*計算所定内深夜時間*/
//				this.prsIncldMidnTime = winthinTime == null || winthinTime.getCalcTime() == null ? 0 
//						: domain.getWithinStatutoryMidNightTime().getTime().getCalcTime().valueAsMinutes();
				/*所定内深夜乖離時間*/
				this.divPrsIncldMidnTime = winthinTime == null || winthinTime.getDivergenceTime() == null ? 0
						: domain.getWithinStatutoryMidNightTime().getTime().getDivergenceTime().valueAsMinutes();
			}
			/*休暇加算時間*/
			this.vactnAddTime = domain.getVacationAddTime() == null ? 0 : domain.getVacationAddTime().valueAsMinutes();
		}
	}
	
	public WithinStatutoryTimeOfDaily toDomain() {
		return WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(new AttendanceTime(this.workTime),
									   									   new AttendanceTime(this.actWorkTime),
									   									   new AttendanceTime(this.prsIncldPrmimTime),
									   									   new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(this.prsIncldMidnTime))),
									   									   new AttendanceTime(this.vactnAddTime));
	}
	
	
}
