/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class KshmtLegaltimeDDefEmpPK.
 */
@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class KshmtLegaltimeDDefEmpPK implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The emp cd. */
	@Column(name = "EMP_CD")
	private String empCd;
	
	public KshmtLegaltimeDDefEmpPK(String cid, String empCd) {
		super();
		this.cid = cid;
		this.empCd = empCd;
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
		hash += (empCd != null ? empCd.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtLegaltimeDDefEmpPK)) {
			return false;
		}
		KshmtLegaltimeDDefEmpPK other = (KshmtLegaltimeDDefEmpPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.empCd == null && other.empCd != null)
				|| (this.empCd != null && !this.empCd.equals(other.empCd))) {
			return false;
		}
		return true;
	}

}
