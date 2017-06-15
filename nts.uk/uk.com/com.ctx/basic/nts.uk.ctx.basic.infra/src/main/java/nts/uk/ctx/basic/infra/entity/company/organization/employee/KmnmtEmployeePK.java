package nts.uk.ctx.basic.infra.entity.company.organization.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KmnmtEmployeePK implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;
	/* 個人ID */
	@Column(name = "PID")
	public String personId;
	/* 社員ID */
	@Column(name = "SID")
	public String employeeId;
	/* 社員CD */
	@Column(name = "SCD")
	public String employeeCode;
}
