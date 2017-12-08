/**
 * 11:12:12 AM Dec 6, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition;

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
@Table(name = "KRCST_ER_AL_APPLICATION")
public class KrcstErAlApplication extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcstErAlApplicationPK krcstErAlApplicationPK;

	@Override
	protected Object getKey() {
		return this.krcstErAlApplicationPK;
	}

}
