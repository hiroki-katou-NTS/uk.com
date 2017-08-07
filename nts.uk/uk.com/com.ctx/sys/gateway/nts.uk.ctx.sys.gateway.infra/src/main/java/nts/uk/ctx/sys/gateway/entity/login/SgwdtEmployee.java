/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.entity.login;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class SgwdtEmployee.
 */
@Getter
@Setter
@Entity
@Table(name = "SGWDT_EMPLOYEE")
@NoArgsConstructor

public class SgwdtEmployee extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected SgwdtEmployeePK sgwdtEmployeePK;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 12)
	@Column(name = "SCD")
	private String scd;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "BUSINESS_NAME")
	private String businessName;

	public SgwdtEmployee(SgwdtEmployeePK sgwdtEmployeePK) {
		this.sgwdtEmployeePK = sgwdtEmployeePK;
	}

	public SgwdtEmployee(SgwdtEmployeePK sgwdtEmployeePK, String scd, String businessName) {
		this.sgwdtEmployeePK = sgwdtEmployeePK;
		this.scd = scd;
		this.businessName = businessName;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (sgwdtEmployeePK != null ? sgwdtEmployeePK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof SgwdtEmployee)) {
			return false;
		}
		SgwdtEmployee other = (SgwdtEmployee) object;
		if ((this.sgwdtEmployeePK == null && other.sgwdtEmployeePK != null)
				|| (this.sgwdtEmployeePK != null && !this.sgwdtEmployeePK.equals(other.sgwdtEmployeePK))) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getKey() {
		return this.sgwdtEmployeePK;
	}
}
