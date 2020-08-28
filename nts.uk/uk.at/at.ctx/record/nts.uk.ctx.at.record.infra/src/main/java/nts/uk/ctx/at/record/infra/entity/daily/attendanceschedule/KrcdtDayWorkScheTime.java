package nts.uk.ctx.at.record.infra.entity.daily.attendanceschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workschedule.WorkScheduleTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_WORK_SCHE_TIME")
public class KrcdtDayWorkScheTime  extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayWorkScheTimePK krcdtDayWorkScheTimePK;
	/*勤務予定時間*/
	@Column(name = "WORK_SCHEDULE_TIME")
	public int workScheduleTime;
	/*計画所定労働時間*/
	@Column(name = "SCHEDULE_PRE_LABOR_TIME")
	public int schedulePreLaborTime;
	/*実績所定労働時間*/
	@Column(name = "RECORE_PRE_LABOR_TIME")
	public int recorePreLaborTime;
	
	@OneToOne(mappedBy="krcdtDayWorkScheTime")
	public KrcdtDayAttendanceTime krcdtDayAttendanceTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayWorkScheTimePK;
	}
	
	public static KrcdtDayWorkScheTime create(String employeeId, GeneralDate date, WorkScheduleTimeOfDaily domain) {
		val entity = new KrcdtDayWorkScheTime();
		/*主キー*/
		entity.krcdtDayWorkScheTimePK = new KrcdtDayWorkScheTimePK(employeeId,date);
		entity.setData(domain);
		return entity;
	}

	public void setData(WorkScheduleTimeOfDaily domain) {
		/*勤務予定時間*/
		this.workScheduleTime = domain.getWorkScheduleTime() == null || domain.getWorkScheduleTime().getTotal() == null ? 0 
				: domain.getWorkScheduleTime().getTotal().valueAsMinutes();
		/*計画所定労働時間*/
		this.schedulePreLaborTime = domain.getSchedulePrescribedLaborTime() == null ? 0 : domain.getSchedulePrescribedLaborTime().valueAsMinutes();
		/*実績所定労働時間*/
		this.recorePreLaborTime = domain.getRecordPrescribedLaborTime() == null ? 0 : domain.getRecordPrescribedLaborTime().valueAsMinutes();
	}
	
	public WorkScheduleTimeOfDaily toDomain() {
		//TODO:  get 合計時間, 所定外時間, 所定内時間 for WorkScheduleTime
		return new WorkScheduleTimeOfDaily(new WorkScheduleTime(new AttendanceTime(this.workScheduleTime), new AttendanceTime(0), new AttendanceTime(0)),
										   new AttendanceTime(this.schedulePreLaborTime),
										   new AttendanceTime(this.recorePreLaborTime));
	}
	
}
