/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstEmpFlexSetPK.
 */

@Setter
@Getter
@Embeddable
public class KshstEmpFlexSetPK implements Serializable {
	
	/** The cid. */
	@Size(min = 1, max = 17)
	@Column(name = "CID")
	private String cid;
	
	/** The emp cd. */
	@Size(min = 1, max = 2)
	@Column(name = "EMP_CD")
	private String empCd;
	
	/** The year. */
	@Column(name = "YEAR")
	private short year;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (empCd != null ? empCd.hashCode() : 0);
		hash += (int) year;
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstEmpFlexSetPK)) {
			return false;
		}
		KshstEmpFlexSetPK other = (KshstEmpFlexSetPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.empCd == null && other.empCd != null) || (this.empCd != null && !this.empCd.equals(other.empCd))) {
			return false;
		}
		if (this.year != other.year) {
			return false;
		}
		return true;
	}

}
