package nts.uk.ctx.at.record.infra.entity.workinformation;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 日別実績の勤務情報.勤務予定時間帯
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WORK_SCHEDULE_TIME")
public class KrcmtWorkScheduleTime extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtWorkScheduleTimePK krcmtWorkScheduleTimePK;
	
	@Column(name = "ATTENDANCE")
	public Integer attendance;

	@Column(name = "LEAVE_WORK")
	public Integer leaveWork;
	
	@ManyToOne
    @JoinColumn(name="SID", referencedColumnName="SID", insertable = false, updatable = false)
	public KrcmtDaiPerWorkInfo daiPerWorkInfo;
	
	public KrcmtWorkScheduleTime(KrcmtWorkScheduleTimePK krcmtWorkScheduleTimePK, Integer attendance,
			Integer leaveWork) {
		super();
		this.krcmtWorkScheduleTimePK = krcmtWorkScheduleTimePK;
		this.attendance = attendance;
		this.leaveWork = leaveWork;
	}

	@Override
	protected Object getKey() {
		return this.krcmtWorkScheduleTimePK;
	}
	
	public ScheduleTimeSheet toDomain() {
		ScheduleTimeSheet domain = new ScheduleTimeSheet(this.krcmtWorkScheduleTimePK.workNo, this.attendance, this.leaveWork);
		return domain;
	}
	
	public static List<ScheduleTimeSheet> toDomain(List<KrcmtWorkScheduleTime> entities) {
		return entities.stream().map(c -> c.toDomain()).collect(Collectors.toList());
	}

}
