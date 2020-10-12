package nts.uk.ctx.at.record.infra.entity.workinformation;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 日別実績の勤務情報.予定時間帯
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_WORK_SCHEDULE_TIME")
public class KrcdtWorkScheduleTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtWorkScheduleTimePK krcdtWorkScheduleTimePK;

	@Column(name = "ATTENDANCE")
	public Integer attendance;

	@Column(name = "LEAVE_WORK")
	public Integer leaveWork;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDaiPerWorkInfo daiPerWorkInfo;

	public KrcdtWorkScheduleTime(KrcdtWorkScheduleTimePK krcmtWorkScheduleTimePK, Integer attendance,
			Integer leaveWork) {
		super();
		this.krcdtWorkScheduleTimePK = krcmtWorkScheduleTimePK;
		this.attendance = attendance;
		this.leaveWork = leaveWork;
	}

	@Override
	protected Object getKey() {
		return this.krcdtWorkScheduleTimePK;
	}

	public ScheduleTimeSheet toDomain() {
		ScheduleTimeSheet domain = new ScheduleTimeSheet(this.krcdtWorkScheduleTimePK.workNo, this.attendance,
				this.leaveWork);
		return domain;
	}

	public static List<ScheduleTimeSheet> toDomain(List<KrcdtWorkScheduleTime> entities) {
		return entities.stream().map(c -> c.toDomain()).collect(Collectors.toList());
	}

	public static KrcdtWorkScheduleTime toEntity(String employeeId, GeneralDate ymd,
			ScheduleTimeSheet scheduleTimeSheet) {
		return new KrcdtWorkScheduleTime(
				new KrcdtWorkScheduleTimePK(employeeId, ymd, scheduleTimeSheet.getWorkNo().v()),
				scheduleTimeSheet.getAttendance().v(), scheduleTimeSheet.getLeaveWork().v());
	}

}
