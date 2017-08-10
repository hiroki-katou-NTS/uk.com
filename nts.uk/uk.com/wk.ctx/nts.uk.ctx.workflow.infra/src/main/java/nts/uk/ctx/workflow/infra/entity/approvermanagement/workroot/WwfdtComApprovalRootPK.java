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
public class WwfdtComApprovalRootPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/*会社ID*/
	@Column(name = "CID")
	public String companyId;
	/*履歴ID*/
	@Column(name = "HIST_ID")
	public String historyId;
	/*申請種類*/
	@Column(name = "APPLICATION_TYPE")
	public int applicationType;
}
