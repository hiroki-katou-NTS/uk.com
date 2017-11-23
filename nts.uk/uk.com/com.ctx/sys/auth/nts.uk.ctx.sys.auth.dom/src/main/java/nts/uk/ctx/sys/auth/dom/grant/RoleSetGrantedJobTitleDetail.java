package nts.uk.ctx.sys.auth.dom.grant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetCode;

/**
 * 
 * @author HungTT - ロールセット職位別付与詳細
 *
 */

@NoArgsConstructor
@Getter
public class RoleSetGrantedJobTitleDetail extends DomainObject {

	// ロールセットコード.
	private RoleSetCode roleSetCd;

	// 職位ID
	private String jobTitleId;

	//会社ID
	private String companyId;
	
	public RoleSetGrantedJobTitleDetail(String roleSetCd, String jobTitleId, String companyId) {
		super();
		this.roleSetCd = new RoleSetCode(roleSetCd);
		this.jobTitleId = jobTitleId;
		this.companyId = companyId;
	}
	
}
