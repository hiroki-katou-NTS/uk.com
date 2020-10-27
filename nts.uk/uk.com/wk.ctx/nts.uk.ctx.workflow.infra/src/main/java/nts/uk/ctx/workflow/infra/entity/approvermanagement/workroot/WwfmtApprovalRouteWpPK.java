package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author hoatt
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class WwfmtApprovalRouteWpPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/**会社ID*/
	@Column(name = "CID")
	public String companyId;
	/**承認ID*/
	@Column(name = "APPROVAL_ID")
	public String approvalId;
	/**職場ID*/
	@Column(name = "WKPID")
	public String workplaceId;
	/**履歴ID*/
	@Column(name = "HIST_ID")
	public String historyId;
}
