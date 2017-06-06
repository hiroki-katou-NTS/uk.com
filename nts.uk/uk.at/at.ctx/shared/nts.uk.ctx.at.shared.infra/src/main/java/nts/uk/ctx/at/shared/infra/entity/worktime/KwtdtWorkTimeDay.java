package nts.uk.ctx.at.shared.infra.entity.worktime;

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
	
	@Column(name="AM_START_TIME")
	public int a_m_StartTime;
	
	@Column(name="AM_START_ATR")
	public int a_m_StartAtr;
	
	@Column(name="AM_END_TIME")
	public int a_m_EndTime;
	
	@Column(name="AM_END_ATR")
	public int a_m_EndAtr;
	
	@Column(name="AM_USE_ATR")
	public int a_m_UseAtr;
	
	@Column(name="PM_START_TIME")
	public int p_m_StartTime;
	
	@Column(name="PM_START_ATR")
	public int p_m_StartAtr;
	
	@Column(name="PM_END_TIME")
	public int p_m_EndTime;
	
	@Column(name="PM_END_ATR")
	public int p_m_EndAtr;
	
	@Column(name="PM_USE_ATR")
	public int p_m_Use_Atr;

	@Override
	protected Object getKey() {
		return kwtdpWorkTimeDayPK;
	}
	
}
