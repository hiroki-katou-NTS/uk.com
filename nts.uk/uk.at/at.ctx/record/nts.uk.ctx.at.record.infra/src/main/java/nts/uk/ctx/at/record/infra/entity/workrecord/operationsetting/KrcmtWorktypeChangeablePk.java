/**
 * 
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author danpv
 *
 */
@Embeddable
public class KrcmtWorktypeChangeablePk {

	@Column(name = "CID")
	public String cid;

	@Column(name = "EMP_CODE")
	public String empCode;

	@Column(name = "WORKTYPE_GROUP_NO")
	public BigDecimal worktypeGroupNo;

	@Column(name = "WORKTYPE_CODE")
	public String worktypeCode;

}
