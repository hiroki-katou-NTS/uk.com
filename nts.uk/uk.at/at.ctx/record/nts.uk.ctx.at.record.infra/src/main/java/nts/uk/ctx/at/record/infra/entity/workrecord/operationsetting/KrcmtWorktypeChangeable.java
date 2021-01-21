/**
 * 
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author danpv
 *
 */
@Entity
@Table(name = "KRCMT_WORKTYPE_CHANGEABLE")
public class KrcmtWorktypeChangeable extends ContractUkJpaEntity {

	@EmbeddedId
	public KrcmtWorktypeChangeablePk pk;

	@Column(name = "WORKTYPE_GROUP_NAME")
	public String workTypeGroupName;

	public KrcmtWorktypeChangeable() {
		super();
	}

	public KrcmtWorktypeChangeable(KrcmtWorktypeChangeablePk pk, String workTypeGroupName) {
		super();
		this.pk = pk;
		this.workTypeGroupName = workTypeGroupName;
	}

	@Override
	protected Object getKey() {
		return pk;
	}

}
