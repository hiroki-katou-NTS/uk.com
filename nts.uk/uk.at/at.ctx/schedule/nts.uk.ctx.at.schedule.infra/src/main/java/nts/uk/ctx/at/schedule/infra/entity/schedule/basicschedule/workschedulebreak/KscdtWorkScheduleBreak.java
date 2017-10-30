package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workschedulebreak;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 勤務予定休憩
 * 
 * @author sonnh1
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KSCDT_WORK_SCHEDULE_BREAK")
public class KscdtWorkScheduleBreak extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscdtWorkScheduleBreakPK kscdtWorkScheduleBreakPk;

	@Column(name = "SCHEDULE_START_CLOCK")
	public int scheduleStartClock;

	@Column(name = "SCHEDULE_START_DAY_ATR")
	public int scheduleStartDayAtr;

	@Column(name = "SCHEDULE_END_CLOCK")
	public int scheduleEndClock;

	@Column(name = "SCHEDULE_END_DAY_ATR")
	public int scheduleEndDayAtr;

	@Override
	protected Object getKey() {
		return this.kscdtWorkScheduleBreakPk;
	}

}
