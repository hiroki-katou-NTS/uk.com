/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class ReportPclmtPersonEmpContractPK.
 */
@Embeddable
@Getter
@Setter
public class ReportPclmtPersonEmpContractPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The ccd. */
	@Column(name = "CCD")
	public String ccd;
	
	/** The p id. */
	@Column(name = "PID")
	public String pId;
	
	/** The str D. */
	@Column(name = "STR_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate strD;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccd == null) ? 0 : ccd.hashCode());
		result = prime * result + ((pId == null) ? 0 : pId.hashCode());
		result = prime * result + ((strD == null) ? 0 : strD.hashCode());
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
		ReportPclmtPersonEmpContractPK other = (ReportPclmtPersonEmpContractPK) obj;
		if (ccd == null) {
			if (other.ccd != null)
				return false;
		} else if (!ccd.equals(other.ccd))
			return false;
		if (pId == null) {
			if (other.pId != null)
				return false;
		} else if (!pId.equals(other.pId))
			return false;
		if (strD == null) {
			if (other.strD != null)
				return false;
		} else if (!strD.equals(other.strD))
			return false;
		return true;
	}
}
