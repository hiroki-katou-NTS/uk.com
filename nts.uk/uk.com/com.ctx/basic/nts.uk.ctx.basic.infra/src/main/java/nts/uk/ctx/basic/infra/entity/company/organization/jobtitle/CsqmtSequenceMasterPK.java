/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.jobtitle;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * The Class CsqmtSequenceMasterPK.
 */
@Data
@Embeddable
public class CsqmtSequenceMasterPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The company id. */
	@Basic(optional = false)
	@Column(name = "CID")
	private String companyId;

	/** The sequence code. */
	@Basic(optional = false)
	@Column(name = "SEQ_CD")
	private String sequenceCode;

	/**
	 * Instantiates a new csqmt sequence master PK.
	 *
	 * @param companyId the company id
	 * @param sequenceCode the sequence code
	 */
	public CsqmtSequenceMasterPK(String companyId, String sequenceCode) {
		super();
		this.companyId = companyId;
		this.sequenceCode = sequenceCode;
	}

	/**
	 * Instantiates a new csqmt sequence master PK.
	 */
	public CsqmtSequenceMasterPK() {
		super();
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
		CsqmtSequenceMasterPK other = (CsqmtSequenceMasterPK) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (sequenceCode == null) {
			if (other.sequenceCode != null)
				return false;
		} else if (!sequenceCode.equals(other.sequenceCode))
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
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((sequenceCode == null) ? 0 : sequenceCode.hashCode());
		return result;
	}

}
