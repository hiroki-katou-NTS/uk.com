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

import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT
 *
 */

@Entity
@Table(name = "SAUMT_ROLESET_JOB")
public class SaumtRoleSetGrantedJobTitle extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@Column(name = "APPLY_CONCURRENT_PERSON")
	public boolean applyToConcurrentPerson;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="roleSetGrantedJobTitle", orphanRemoval = true)
	public List<SaumtRoleSetGrantedJobTitleDetail> details;

	@Override
	protected Object getKey() {
		return this.companyId;
	}

	public SaumtRoleSetGrantedJobTitle(String companyId, boolean applyToConcurrentPerson, List<SaumtRoleSetGrantedJobTitleDetail> details) {
		super();
		this.companyId = companyId;
		this.applyToConcurrentPerson = applyToConcurrentPerson;
		this.details = details;
	}

}
