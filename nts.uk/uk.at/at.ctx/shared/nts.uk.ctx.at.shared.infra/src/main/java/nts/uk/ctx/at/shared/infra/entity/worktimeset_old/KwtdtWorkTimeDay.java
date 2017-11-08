package nts.uk.ctx.at.shared.infra.entity.worktimeset_old;

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
	
	@Column(name="START_TIME")
	public int start;
	
	@Column(name="END_TIME")
	public int end;
	
	@Override
	protected Object getKey() {
		return kwtdpWorkTimeDayPK;
	}
	
}
