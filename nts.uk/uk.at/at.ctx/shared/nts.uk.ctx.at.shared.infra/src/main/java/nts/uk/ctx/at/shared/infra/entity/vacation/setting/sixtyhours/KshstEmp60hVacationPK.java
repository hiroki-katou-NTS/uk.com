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
 * The Class KshstEmp60hVacationPK.
 */
@Getter
@Setter
@Embeddable
public class KshstEmp60hVacationPK {

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The contract type cd. */
	@Column(name = "EMPCD")
	private String contractTypeCd;

	/**
	 * Instantiates a new kshst emp 60 h vacation PK.
	 */
	public KshstEmp60hVacationPK() {
	}

	/**
	 * Instantiates a new kshst emp 60 h vacation PK.
	 *
	 * @param cid the cid
	 * @param contractTypeCd the contract type cd
	 */
	public KshstEmp60hVacationPK(String cid, String contractTypeCd) {
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
		if (!(object instanceof KshstEmp60hVacationPK)) {
			return false;
		}
		KshstEmp60hVacationPK other = (KshstEmp60hVacationPK) object;
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
