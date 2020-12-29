/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmfmtRetentionEmpCtrPK.
 */
@Setter
@Getter
@Embeddable
public class KmfmtRetentionEmpCtrPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The cid. */
	@NotNull
	@Column(name = "CID")
	private String cid;

	/** The emp ctr cd. */
	@NotNull
	@Column(name = "EMPCD")
	private String empCtrCd;

	/**
	 * Instantiates a new kmfmt retention emp ctr PK.
	 */
	public KmfmtRetentionEmpCtrPK() {
		super();
	}

	/**
	 * Instantiates a new kmfmt retention emp ctr PK.
	 *
	 * @param cid
	 *            the cid
	 * @param empCtrCd
	 *            the emp ctr cd
	 */
	public KmfmtRetentionEmpCtrPK(String cid, String empCtrCd) {
		this.cid = cid;
		this.empCtrCd = empCtrCd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (empCtrCd != null ? empCtrCd.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KmfmtRetentionEmpCtrPK)) {
			return false;
		}
		KmfmtRetentionEmpCtrPK other = (KmfmtRetentionEmpCtrPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.empCtrCd == null && other.empCtrCd != null)
				|| (this.empCtrCd != null && !this.empCtrCd.equals(other.empCtrCd))) {
			return false;
		}
		return true;
	}
}
