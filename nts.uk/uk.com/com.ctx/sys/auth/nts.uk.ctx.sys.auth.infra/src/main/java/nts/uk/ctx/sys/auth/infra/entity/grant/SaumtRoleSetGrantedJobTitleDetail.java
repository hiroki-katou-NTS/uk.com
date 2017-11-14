package nts.uk.ctx.sys.auth.infra.entity.grant;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtRoleSetPK;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author Admin
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "SAUMT_ROLESET_JOB_DETAIL")
public class SaumtRoleSetGrantedJobTitleDetail extends UkJpaEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SaumtRoleSetGrantedJobTitleDetailPK roleSetGrantedJobTitleDetailPK;

	@Override
	protected Object getKey() {
		return this.roleSetGrantedJobTitleDetailPK;
	}

	public SaumtRoleSetGrantedJobTitleDetail(String roleSetCd, String jobTitleId, String companyId) {
		super();
		this.roleSetGrantedJobTitleDetailPK = new SaumtRoleSetGrantedJobTitleDetailPK(roleSetCd, jobTitleId, companyId);
	}
	
}
