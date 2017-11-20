package nts.uk.ctx.sys.auth.infra.entity.grant.rolesetjob;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT
 *
 */

@Entity
@Table(name = "SAUMT_ROLESET_JOB_DETAIL")
public class SaumtRoleSetGrantedJobTitleDetail extends UkJpaEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SaumtRoleSetGrantedJobTitleDetailPK roleSetGrantedJobTitleDetailPK;

	@ManyToOne
	@JoinColumns({
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
    })
	public SaumtRoleSetGrantedJobTitle roleSetGrantedJobTitle;
	
	@Override
	protected Object getKey() {
		return this.roleSetGrantedJobTitleDetailPK;
	}

	public SaumtRoleSetGrantedJobTitleDetail(String roleSetCd, String jobTitleId, String companyId) {
		super();
		this.roleSetGrantedJobTitleDetailPK = new SaumtRoleSetGrantedJobTitleDetailPK(roleSetCd, jobTitleId, companyId);
	}
	
}
