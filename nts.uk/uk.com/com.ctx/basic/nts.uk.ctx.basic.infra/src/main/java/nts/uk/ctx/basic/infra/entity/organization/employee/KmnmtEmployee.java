package nts.uk.ctx.basic.infra.entity.organization.employee;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KMNMT_EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
public class KmnmtEmployee extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KmnmtEmployeePK kmnmtEmployeePK;

	@Basic(optional = false)
	@Column(name = "S_MAIL")
	public String employeeMail;

	@Basic(optional = false)
	@Column(name = "RETIREMENT_DATE")
	public GeneralDate retirementDate;

	@Basic(optional = false)
	@Column(name = "JOIN_DATE")
	public GeneralDate joinDate;

	@Override
	protected Object getKey() {
		return kmnmtEmployeePK;
	}

}
