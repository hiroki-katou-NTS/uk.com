/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class KmfmtOccurVacationSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Basic(optional = false)
	@Column(name = "CID")
	private String cid;

	/** The occurr division. */
	@Basic(optional = false)
	@Column(name = "OCCURR_DIVISION")
	private Integer occurrDivision;

	/**
	 * Instantiates a new qismt social insu office PK.
	 */
	public KmfmtOccurVacationSetPK() {
		super();
	}

	/**
	 * Instantiates a new qismt social insu office PK.
	 *
	 * @param ccd
	 *            the ccd
	 * @param siOfficeCd
	 *            the si office cd
	 */
	public KmfmtOccurVacationSetPK(String cid, Integer occurrDivision) {
		this.cid = cid;
		this.occurrDivision = occurrDivision;
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
		if (!(obj instanceof KmfmtOccurVacationSetPK))
			return false;
		KmfmtOccurVacationSetPK other = (KmfmtOccurVacationSetPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (occurrDivision == null) {
			if (other.occurrDivision != null)
				return false;
		} else if (!occurrDivision.equals(other.occurrDivision))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((occurrDivision == null) ? 0 : occurrDivision.hashCode());
		return result;
	}

	
}

