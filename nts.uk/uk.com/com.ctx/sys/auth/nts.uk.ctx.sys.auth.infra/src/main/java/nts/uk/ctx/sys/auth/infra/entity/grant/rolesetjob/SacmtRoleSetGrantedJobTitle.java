package nts.uk.ctx.sys.auth.infra.entity.grant.rolesetjob;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author HungTT
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "SACMT_ROLESET_JOB")
public class SacmtRoleSetGrantedJobTitle extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@Column(name = "APPLY_CONCURRENT_PERSON")
	public boolean applyToConcurrentPerson;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="roleSetGrantedJobTitle", orphanRemoval = true)
	public List<SacmtRoleSetGrantedJobTitleDetail> details;

	@Override
	protected Object getKey() {
		return this.companyId;
	}

	public SacmtRoleSetGrantedJobTitle(String companyId, boolean applyToConcurrentPerson, List<SacmtRoleSetGrantedJobTitleDetail> details) {
		super();
		this.companyId = companyId;
		this.applyToConcurrentPerson = applyToConcurrentPerson;
		this.details = details;
	}
	
}
