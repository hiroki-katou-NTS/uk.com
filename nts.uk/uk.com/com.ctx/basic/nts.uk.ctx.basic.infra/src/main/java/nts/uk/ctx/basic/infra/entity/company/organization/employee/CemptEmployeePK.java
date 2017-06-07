/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CemptEmployeePK.
 */
@Setter
@Getter
@Embeddable
public class CemptEmployeePK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccid. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CCID")
	private String ccid;

	/** The pid. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "PID")
	private String pid;

	/** The empid. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "EMPID")
	private String empid;

	/** The empcd. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "EMPCD")
	private String empcd;

	/**
	 * Instantiates a new cempt employee PK.
	 */
	public CemptEmployeePK() {
	}

	/**
	 * Instantiates a new cempt employee PK.
	 *
	 * @param ccid
	 *            the ccid
	 * @param pid
	 *            the pid
	 * @param empid
	 *            the empid
	 * @param empcd
	 *            the empcd
	 */
	public CemptEmployeePK(String ccid, String pid, String empid, String empcd) {
		this.ccid = ccid;
		this.pid = pid;
		this.empid = empid;
		this.empcd = empcd;
	}

	/**
	 * Gets the ccid.
	 *
	 * @return the ccid
	 */
	public String getCcid() {
		return ccid;
	}

	/**
	 * Sets the ccid.
	 *
	 * @param ccid
	 *            the new ccid
	 */
	public void setCcid(String ccid) {
		this.ccid = ccid;
	}

	/**
	 * Gets the pid.
	 *
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * Sets the pid.
	 *
	 * @param pid
	 *            the new pid
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * Gets the empid.
	 *
	 * @return the empid
	 */
	public String getEmpid() {
		return empid;
	}

	/**
	 * Sets the empid.
	 *
	 * @param empid
	 *            the new empid
	 */
	public void setEmpid(String empid) {
		this.empid = empid;
	}

	/**
	 * Gets the empcd.
	 *
	 * @return the empcd
	 */
	public String getEmpcd() {
		return empcd;
	}

	/**
	 * Sets the empcd.
	 *
	 * @param empcd
	 *            the new empcd
	 */
	public void setEmpcd(String empcd) {
		this.empcd = empcd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (ccid != null ? ccid.hashCode() : 0);
		hash += (pid != null ? pid.hashCode() : 0);
		hash += (empid != null ? empid.hashCode() : 0);
		hash += (empcd != null ? empcd.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof CemptEmployeePK)) {
			return false;
		}
		CemptEmployeePK other = (CemptEmployeePK) object;
		if ((this.ccid == null && other.ccid != null)
			|| (this.ccid != null && !this.ccid.equals(other.ccid))) {
			return false;
		}
		if ((this.pid == null && other.pid != null)
			|| (this.pid != null && !this.pid.equals(other.pid))) {
			return false;
		}
		if ((this.empid == null && other.empid != null)
			|| (this.empid != null && !this.empid.equals(other.empid))) {
			return false;
		}
		if ((this.empcd == null && other.empcd != null)
			|| (this.empcd != null && !this.empcd.equals(other.empcd))) {
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
		return "entity.CemptEmployeePK[ ccid=" + ccid + ", pid=" + pid + ", empid=" + empid
			+ ", empcd=" + empcd + " ]";
	}

}
