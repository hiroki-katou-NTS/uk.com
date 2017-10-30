package nts.uk.ctx.at.auth.infra.entity.employmentrole;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRole;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KACMT_EMPLOYMENT_ROLE")
public class KacmtEmploymentRole extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = -5374494134003331017L;

	@EmbeddedId
	public KacmtEmploymentRolePK kacmtEmploymentRolePK;

	@Override
	protected Object getKey() {
		return this.kacmtEmploymentRolePK;
	}

	public EmploymentRole toDomain() {
		return EmploymentRole.createFromJavaType(this.kacmtEmploymentRolePK.companyID,
				this.kacmtEmploymentRolePK.roleID);
	}

}
