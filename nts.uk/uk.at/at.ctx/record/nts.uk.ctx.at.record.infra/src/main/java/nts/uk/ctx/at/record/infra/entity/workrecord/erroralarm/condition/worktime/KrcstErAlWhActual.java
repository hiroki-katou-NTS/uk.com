/**
 * 5:07:18 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktime;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KRCST_ER_AL_WH_ACTUAL")
public class KrcstErAlWhActual extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    public KrcstErAlWhPlanActualPK krcstErAlWhPlanActualPK;

	@Override
	protected Object getKey() {
		return this.krcstErAlWhPlanActualPK;
	}
	
}
