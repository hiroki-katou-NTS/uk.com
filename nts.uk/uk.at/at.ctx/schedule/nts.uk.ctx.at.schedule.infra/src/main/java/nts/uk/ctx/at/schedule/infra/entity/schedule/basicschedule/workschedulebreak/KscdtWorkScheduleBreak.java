package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workschedulebreak;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定休憩
 * 
 * @author sonnh1
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KSCDT_SCHE_BREAK")
public class KscdtWorkScheduleBreak extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscdtWorkScheduleBreakPK kscdtWorkScheduleBreakPk;

	@Column(name = "START_CLOCK")
	public int scheduleStartClock;

	@Column(name = "END_CLOCK")
	public int scheduleEndClock;
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "SID", referencedColumnName = "KSCDT_SCHE_BASIC.SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "KSCDT_SCHE_BASIC.YMD", insertable = false, updatable = false) })
	public KscdtBasicSchedule kscdtBasicSchedule;

	@Override
	protected Object getKey() {
		return this.kscdtWorkScheduleBreakPk;
	}

	public KscdtWorkScheduleBreak(KscdtWorkScheduleBreakPK kscdtWorkScheduleBreakPk, int scheduleStartClock,
			int scheduleEndClock) {
		super();
		this.kscdtWorkScheduleBreakPk = kscdtWorkScheduleBreakPk;
		this.scheduleStartClock = scheduleStartClock;
		this.scheduleEndClock = scheduleEndClock;
	}
	
}
