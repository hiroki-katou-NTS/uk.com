/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


/**
 * The Class KcbmtCompanyWorkSetPK.
 */
@Setter
@Getter
@Embeddable
public class KcbmtCompanyWorkSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@NotNull
	@Column(name = "CID")
	private String cid;
	
	/** The workday division. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WORKDAY_DIVISION")
	private Integer workdayDivision;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((workdayDivision == null) ? 0 : workdayDivision.hashCode());
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
		KcbmtCompanyWorkSetPK other = (KcbmtCompanyWorkSetPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (workdayDivision == null) {
			if (other.workdayDivision != null)
				return false;
		} else if (!workdayDivision.equals(other.workdayDivision))
			return false;
		return true;
	}
	
	
}
