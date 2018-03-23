/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstEmpTransLabTimePK.
 */

@Setter
@Getter
@AllArgsConstructor
@Embeddable
public class KshstEmpTransLabTimePK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;
	
	/** The emp cd. */
	@Column(name = "EMP_CD")
	private String empCd;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (empCd != null ? empCd.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstEmpTransLabTimePK)) {
			return false;
		}
		KshstEmpTransLabTimePK other = (KshstEmpTransLabTimePK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.empCd == null && other.empCd != null) || (this.empCd != null && !this.empCd.equals(other.empCd))) {
			return false;
		}
		return true;
	}

}
