package nts.uk.ctx.pereg.infra.entity.roles.auth.item;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_ROLE_ITEM_AUTH")
@Entity
public class PpemtRoleItemAuth extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtRoleItemAuthPk ppemtRoleItemAuthPk;

	@Basic(optional = false)
	@Column(name = "OTHER_PERSON_AUTH_TYPE")
	public int otherPersonAuthType;

	@Basic(optional = false)
	@Column(name = "SELF_AUTH_TYPE")
	public int selfAuthType;

	@Override
	protected Object getKey() {
		return this.ppemtRoleItemAuthPk;
	}

	public PpemtRoleItemAuth updateFromDomain(PersonInfoItemAuth domain) {
		this.otherPersonAuthType = domain.getOtherAuth().value;
		this.selfAuthType = domain.getSelfAuth().value;
		return this;
	}

}
