/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class CemptEmployee.
 */
@Getter
@Setter
@Entity
@Table(name = "CEMPT_EMPLOYEE")
@XmlRootElement
public class CemptEmployee extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cempt employee PK. */
	@EmbeddedId
	protected CemptEmployeePK cemptEmployeePK;

	/** The join date. */
	@Column(name = "JOIN_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate joinDate;

	/** The empmail adrr. */
	@Column(name = "EMPMAIL_ADRR")
	private String empmailAdrr;

	/** The retirement date. */
	@Column(name = "RETIREMENT_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate retirementDate;

	/**
	 * Instantiates a new cempt employee.
	 */
	public CemptEmployee() {
	}

	/**
	 * Instantiates a new cempt employee.
	 *
	 * @param cemptEmployeePK the cempt employee PK
	 */
	public CemptEmployee(CemptEmployeePK cemptEmployeePK) {
		this.cemptEmployeePK = cemptEmployeePK;
	}

	/**
	 * Instantiates a new cempt employee.
	 *
	 * @param ccid the ccid
	 * @param pid the pid
	 * @param empid the empid
	 * @param empcd the empcd
	 */
	public CemptEmployee(String ccid, String pid, String empid, String empcd) {
		this.cemptEmployeePK = new CemptEmployeePK(ccid, pid, empid, empcd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cemptEmployeePK != null ? cemptEmployeePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof CemptEmployee)) {
			return false;
		}
		CemptEmployee other = (CemptEmployee) object;
		if ((this.cemptEmployeePK == null && other.cemptEmployeePK != null)
			|| (this.cemptEmployeePK != null
				&& !this.cemptEmployeePK.equals(other.cemptEmployeePK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.CemptEmployee[ cemptEmployeePK=" + cemptEmployeePK + " ]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getCemptEmployeePK();
	}

}
