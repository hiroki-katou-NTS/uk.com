package nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.onedayfavoriteset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TASK_FAV_DAY_SET_TS")
public class KrcdtTaskFavDaySetTs extends ContractCompanyUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtTaskFavDaySetTsPk pk;

	@Column(name = "END_CLOCK")
	public int endClock;

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
