package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class WwfmtJobSearchPK implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**会社ID*/
	@Column(name = "CID")
	public String companyId;
	/**職位ID*/
	@Column(name = "JOB_ID")
	public String jobId;
}
