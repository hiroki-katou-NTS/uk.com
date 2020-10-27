/**
 * 9:17:10 AM Mar 13, 2018
 */
package nts.uk.ctx.at.record.infra.entity.approvalmanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author hungnm
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtBossCheckNotJobPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String cId;
	
	@Column(name = "JOBID")
	public String jobId;

}
