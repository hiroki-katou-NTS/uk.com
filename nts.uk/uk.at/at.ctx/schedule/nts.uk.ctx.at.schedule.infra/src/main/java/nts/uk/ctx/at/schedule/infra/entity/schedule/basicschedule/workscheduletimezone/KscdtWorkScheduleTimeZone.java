package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定時間帯
 * 
 * @author sonnh1
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KSCDT_SCHE_TIMEZONE")
public class KscdtWorkScheduleTimeZone extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscdtWorkScheduleTimeZonePK kscdtWorkScheduleTimeZonePk;

	@Column(name = "BOUNCE_ATR")
	public int bounceAtr;

	@Column(name = "START_CLOCK")
	public int scheduleStartClock;

	@Column(name = "END_CLOCK")
	public int scheduleEndClock;

	@Override
	protected Object getKey() {
		return this.kscdtWorkScheduleTimeZonePk;
	}
}
