/**
 * 
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.emplrole;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author danpv
 *
 */
@Embeddable
public class EmpRolePk {
	
	@Column(name = "CID")
	public String cid;
	
	@Column(name = "ROLE_ID")
	public String roleId;
	
	public EmpRolePk() {
		super();
	}
	
	public EmpRolePk(String cid, String roleId) {
		super();
		this.cid = cid;
		this.roleId = roleId;
	}

}
