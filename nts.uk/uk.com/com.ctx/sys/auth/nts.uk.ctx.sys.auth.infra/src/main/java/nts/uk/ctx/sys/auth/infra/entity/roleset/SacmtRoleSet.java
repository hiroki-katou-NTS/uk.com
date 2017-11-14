/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.entity.roleset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * Class entity of table SacmtRoleSet/SACMT_ROLE_SET
 * @author Hieu.NV
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SACMT_ROLE_SET")
public class SacmtRoleSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SacmtRoleSetPK roleSetPK;

	@Basic(optional = false)
	@Column(name = "ROLE_SET_NAME")
	public String roleSetName;

	@Basic(optional = false)
	@Column(name = "APPROVAL_AUTHORITY")
	public int approvalAuthority;

	@Basic(optional = false)
	@Column(name = "OFFICE_HELPER_ROLE")
	public String officeHelperRole;

	@Basic(optional = false)
	@Column(name = "MY_NUMBER_ROLE")
	public String myNumberRole;

	@Basic(optional = false)
	@Column(name = "HR_ROLE")
	public String hRRole;

	@Basic(optional = false)
	@Column(name = "PERSON_INF_ROLE")
	public String personInfRole;

	@Basic(optional = false)
	@Column(name = "EMPLOYMENT_ROLE")
	public String employmentRole;

	@Basic(optional = false)
	@Column(name = "SALARY_ROLE")
	public String salaryRole;

	@Override
	protected Object getKey() {
		return roleSetPK;
	}

	/**
	 * Build Entity
	 * @param roleSetPK
	 * @param roleSetName
	 * @param approvalAuthority
	 * @param officeHelperRole
	 * @param myNumberRole
	 * @param hRRole
	 * @param personInfRole
	 * @param employmentRole
	 * @param salaryRole
	 */
	public void BuildEntity(SacmtRoleSetPK roleSetPK
			, String roleSetName
			, int approvalAuthority
			, String officeHelperRole
			, String myNumberRole
			, String hRRole
			, String personInfRole
			, String employmentRole
			, String salaryRole) {
		this.roleSetPK = roleSetPK;
		this.roleSetName = roleSetName;
		this.approvalAuthority = approvalAuthority;
		this.officeHelperRole = officeHelperRole;
		this.myNumberRole = myNumberRole;
		this.hRRole = hRRole;
		this.personInfRole = personInfRole;
		this.employmentRole = employmentRole;
		this.salaryRole = salaryRole;
	}
}
