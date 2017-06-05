package nts.uk.ctx.sys.portal.infra.entity.toppage.setting;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CcgptTopPageJobSetPK {
	/** The companyId. */
	@Id
	@Column(name = "CID")
	public String companyId;
	
	/** The top menu no. */
	@Column(name = "TOP_MENU_NO")
	public String topMenuNo;
	
	/** The login menu no. */
	@Column(name = "LOGIN_MENU_NO")
	public String loginMenuNo;
	
	/** The job id. */
	@Column(name = "JOBID")
	public String jobId;
}
