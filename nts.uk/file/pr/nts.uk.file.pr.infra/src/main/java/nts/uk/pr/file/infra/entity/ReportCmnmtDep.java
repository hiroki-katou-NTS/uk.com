/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ReportCmnmtDep.
 */
@Entity
@Setter
@Getter
@Table(name = "CMNMT_DEP")
public class ReportCmnmtDep implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cmnmt dep PK. */
	@EmbeddedId
	private ReportCmnmtDepPK cmnmtDepPK;

	/** The start date. */
	@Column(name = "STR_D")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	/** The end date. */
	@Column(name = "END_D")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	/** The name. */
	@Column(name = "DEPNAME")
	private String name;

	/** The short name. */
	@Column(name = "DEPNAME_ABB")
	private String shortName;

	/** The generic name. */
	@Column(name = "DEPNAME_TOTAL")
	private String genericName;

	/** The hierarchy id. */
	@Column(name = "HIERARCHY_ID")
	private String hierarchyId;

	/** The hierarchy id 01. */
	@Column(name = "HIERARCHY_ID_01")
	private String hierarchyId01;

	/** The hierarchy id 02. */
	@Column(name = "HIERARCHY_ID_02")
	private String hierarchyId02;

	/** The hierarchy id 03. */
	@Column(name = "HIERARCHY_ID_03")
	private String hierarchyId03;

	/** The hierarchy id 04. */
	@Column(name = "HIERARCHY_ID_04")
	private String hierarchyId04;

	/** The hierarchy id 05. */
	@Column(name = "HIERARCHY_ID_05")
	private String hierarchyId05;

	/** The hierarchy id 06. */
	@Column(name = "HIERARCHY_ID_06")
	private String hierarchyId06;

	/** The hierarchy id 07. */
	@Column(name = "HIERARCHY_ID_07")
	private String hierarchyId07;

	/** The hierarchy id 08. */
	@Column(name = "HIERARCHY_ID_08")
	private String hierarchyId08;

	/** The hierarchy id 09. */
	@Column(name = "HIERARCHY_ID_09")
	private String hierarchyId09;

	/** The hierarchy id 10. */
	@Column(name = "HIERARCHY_ID_10")
	private String hierarchyId10;

	/** The external code. */
	@Column(name = "DEP_OUT_CD")
	private String externalCode;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmnmtDepPK == null) ? 0 : cmnmtDepPK.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportCmnmtDep other = (ReportCmnmtDep) obj;
		if (cmnmtDepPK == null) {
			if (other.cmnmtDepPK != null)
				return false;
		} else if (!cmnmtDepPK.equals(other.cmnmtDepPK))
			return false;
		return true;
	}
	
	
}
