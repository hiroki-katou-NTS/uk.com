package nts.uk.ctx.sys.portal.infra.entity.jobtitletying;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * @author yennth
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CcgstJobTitleTyingPK {
	@Column(name = "CID")
	public String companyId;
	
	/** The job id. */
	@Column(name = "JOBID")
	public String jobId;
}
