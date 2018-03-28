/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class KshstWkpNormalSetPK.
 */

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class KshstWkpNormalSetPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Column(name = "CID")
	private String cid;
	
	/** The wkp id. */
	@Column(name = "WKP_ID")
	private String wkpId;
	
	/** The year. */
	@Column(name = "YEAR")
	private int year;

	public KshstWkpNormalSetPK(String cid, String wkpId, int year) {
		super();
		this.cid = cid;
		this.wkpId = wkpId;
		this.year = year;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (wkpId != null ? wkpId.hashCode() : 0);
		hash += (int) year;
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstWkpNormalSetPK)) {
			return false;
		}
		KshstWkpNormalSetPK other = (KshstWkpNormalSetPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.wkpId == null && other.wkpId != null) || (this.wkpId != null && !this.wkpId.equals(other.wkpId))) {
			return false;
		}
		if (this.year != other.year) {
			return false;
		}
		return true;
	}

}
