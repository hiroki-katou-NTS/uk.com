package nts.uk.ctx.pereg.infra.entity.roles;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_PERSON_ROLE")
@Entity
public class PpemtPersonRole extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtPersonRolePk ppemtPersonRolePk;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;
	
	@Basic(optional = false)
	@Column(name = "ROLE_CD")
	public String roleCode;
	
	@Basic(optional = false)
	@Column(name = "ROLE_NAME")
	public String roleName;

	@Override
	protected Object getKey() {
		return this.ppemtPersonRolePk;
	}

}
