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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt 日別実績の勤務情報.予定時間帯
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_TS_ATD_SCHE")
public class KrcdtDayTsAtdSche extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDayTsAtdSchePK krcdtDayTsAtdSchePK;

	@Column(name = "ATTENDANCE")
	public Integer attendance;

	@Column(name = "LEAVE_WORK")
	public Integer leaveWork;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "YMD", insertable = false, updatable = false) })
	public KrcdtDayInfoPerWork daiPerWorkInfo;

	public KrcdtDayTsAtdSche(KrcdtDayTsAtdSchePK krcmtWorkScheduleTimePK, Integer attendance,
			Integer leaveWork) {
		super();
		this.krcdtDayTsAtdSchePK = krcmtWorkScheduleTimePK;
		this.attendance = attendance;
		this.leaveWork = leaveWork;
	}

	@Override
	protected Object getKey() {
		return this.krcdtDayTsAtdSchePK;
	}

	public ScheduleTimeSheet toDomain() {
		ScheduleTimeSheet domain = new ScheduleTimeSheet(this.krcdtDayTsAtdSchePK.workNo, this.attendance,
				this.leaveWork);
		return domain;
	}

	public static List<ScheduleTimeSheet> toDomain(List<KrcdtDayTsAtdSche> entities) {
		return entities.stream().map(c -> c.toDomain()).collect(Collectors.toList());
	}

	public static KrcdtDayTsAtdSche toEntity(String employeeId, GeneralDate ymd,
			ScheduleTimeSheet scheduleTimeSheet) {
		return new KrcdtDayTsAtdSche(
				new KrcdtDayTsAtdSchePK(employeeId, ymd, scheduleTimeSheet.getWorkNo().v()),
				scheduleTimeSheet.getAttendance().v(), scheduleTimeSheet.getLeaveWork().v());
	}

}
