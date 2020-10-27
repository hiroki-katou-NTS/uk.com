/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtHd60hEmpPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtHd60hEmpPK {

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The contract type cd. */
	@Column(name = "EMPCD")
	private String contractTypeCd;

	/**
	 * Instantiates a new kshst emp 60 h vacation PK.
	 */
	public KshmtHd60hEmpPK() {
	}

	/**
	 * Instantiates a new kshst emp 60 h vacation PK.
	 *
	 * @param cid the cid
	 * @param contractTypeCd the contract type cd
	 */
	public KshmtHd60hEmpPK(String cid, String contractTypeCd) {
		this.cid = cid;
		this.contractTypeCd = contractTypeCd;
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
		hash += (contractTypeCd != null ? contractTypeCd.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtHd60hEmpPK)) {
			return false;
		}
		KshmtHd60hEmpPK other = (KshmtHd60hEmpPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.contractTypeCd == null && other.contractTypeCd != null)
				|| (this.contractTypeCd != null
						&& !this.contractTypeCd.equals(other.contractTypeCd))) {
			return false;
		}
		return true;
	}

}
