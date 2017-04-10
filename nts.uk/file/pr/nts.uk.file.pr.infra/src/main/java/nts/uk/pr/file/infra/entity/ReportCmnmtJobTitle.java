/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.TableEntity;

/**
 * The Class ReportCmnmtJobTitle.
 */
@Entity
@Setter
@Getter
@Table(name="CMNMT_JOB_TITLE")
public class ReportCmnmtJobTitle extends TableEntity implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cmnmt job title PK. */
	@EmbeddedId
    public ReportCmnmtJobTitlePK cmnmtJobTitlePK;

	/** The exclus version. */
	@Basic(optional = false)
	@Column(name = "EXCLUS_VER")
	public int exclusVersion;
	
	/** The job name. */
	@Basic(optional = false)
	@Column(name = "JOBNAME")
	public String jobName;
	
	/** The presence check scope set. */
	@Basic(optional = false)
	@Column(name = "PRESENCE_CHECK_SCOPE_SET")
	public int presenceCheckScopeSet;
	
	/** The job out code. */
	@Basic(optional = true)
	@Column(name = "JOB_OUT_CD")
	public String jobOutCode;
	
	/** The memo. */
	@Basic(optional = true)
	@Column(name = "MEMO")
	public String memo;
	
	/** The hierarchy order code. */
	@Basic(optional = false)
	@Column(name = "HIERARCHY_ORDER_CD")
	public String hierarchyOrderCode;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmnmtJobTitlePK == null) ? 0 : cmnmtJobTitlePK.hashCode());
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
		ReportCmnmtJobTitle other = (ReportCmnmtJobTitle) obj;
		if (cmnmtJobTitlePK == null) {
			if (other.cmnmtJobTitlePK != null)
				return false;
		} else if (!cmnmtJobTitlePK.equals(other.cmnmtJobTitlePK))
			return false;
		return true;
	}
}