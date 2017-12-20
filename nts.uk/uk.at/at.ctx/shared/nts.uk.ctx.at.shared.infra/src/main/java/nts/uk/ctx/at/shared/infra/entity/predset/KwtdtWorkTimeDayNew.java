package nts.uk.ctx.at.shared.infra.entity.predset;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Getter
@Setter
@Entity
@Table(name="KWTDT_WORK_TIME_DAY")
public class KwtdtWorkTimeDayNew extends UkJpaEntity{
	
	@EmbeddedId
	public KwtdpWorkTimeDayPKNew kwtdpWorkTimeDayPK;
	
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
