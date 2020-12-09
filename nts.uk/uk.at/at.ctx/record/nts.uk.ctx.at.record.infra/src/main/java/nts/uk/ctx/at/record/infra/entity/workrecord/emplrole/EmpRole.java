/**
 * 
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.emplrole;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author danpv
 *
 */
@Entity
@Table(name = "EMP_ROLE")
public class EmpRole extends ContractUkJpaEntity{
	
	@EmbeddedId
	public EmpRolePk key;
	
	@Column(name = "ROLE_NAME")
	public String roleName;

	@Override
	protected Object getKey() {
		return key;
	}
	
}
