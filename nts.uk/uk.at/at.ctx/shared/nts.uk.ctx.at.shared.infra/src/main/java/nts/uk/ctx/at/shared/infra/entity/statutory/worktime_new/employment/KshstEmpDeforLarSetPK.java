/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class KshstEmpDeforLarSetPK.
 */
@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class KshstEmpDeforLarSetPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The emp cd. */
	@Column(name = "EMP_CD")
	private String empCd;

	/** The year. */
	@Column(name = "YEAR")
	private int year;

	public KshstEmpDeforLarSetPK(String cid, String empCd, int year) {
		super();
		this.cid = cid;
		this.empCd = empCd;
		this.year = year;
	}

	/* (non-Javadoc)
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
		hash += (int) year;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstEmpDeforLarSetPK)) {
			return false;
		}
		KshstEmpDeforLarSetPK other = (KshstEmpDeforLarSetPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.empCd == null && other.empCd != null)
				|| (this.empCd != null && !this.empCd.equals(other.empCd))) {
			return false;
		}
		if (this.year != other.year) {
			return false;
		}
		return true;
	}


}
