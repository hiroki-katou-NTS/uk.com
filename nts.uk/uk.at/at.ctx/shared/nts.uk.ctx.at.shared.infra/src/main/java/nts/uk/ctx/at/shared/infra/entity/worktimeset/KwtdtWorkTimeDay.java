package nts.uk.ctx.at.shared.infra.entity.worktimeset;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KWTDT_WORK_TIME_DAY")
public class KwtdtWorkTimeDay extends UkJpaEntity{
	
	@EmbeddedId
	public KwtdpWorkTimeDayPK kwtdpWorkTimeDayPK;
	
	@Column(name="USE_ATR")
	public int useAtr;
	
	@Column(name="AM_START_CLOCK")
	public int a_m_StartClock;
	
	@Column(name="AM_START_ATR")
	public int a_m_StartAtr;
	
	@Column(name="AM_END_CLOCK")
	public int a_m_EndClock;
	
	@Column(name="AM_END_ATR")
	public int a_m_EndAtr;
	
	@Column(name="PM_START_CLOCK")
	public int p_m_StartClock;
	
	@Column(name="PM_START_ATR")
	public int p_m_StartAtr;
	
	@Column(name="PM_END_CLOCK")
	public int p_m_EndClock;
	
	@Column(name="PM_END_ATR")
	public int p_m_EndAtr;

	@Override
	protected Object getKey() {
		return kwtdpWorkTimeDayPK;
	}
	
}
