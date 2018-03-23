/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class KshstShaNormalSetPK.
 */

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class KshstShaNormalSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The sid. */
	@Column(name = "SID")
	private String sid;

	/** The year. */
	@Column(name = "YEAR")
	private int year;

	public KshstShaNormalSetPK(String cid, String sid, int year) {
		super();
		this.cid = cid;
		this.sid = sid;
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
		hash += (sid != null ? sid.hashCode() : 0);
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
		// not set
		if (!(object instanceof KshstShaNormalSetPK)) {
			return false;
		}
		KshstShaNormalSetPK other = (KshstShaNormalSetPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.sid == null && other.sid != null)
				|| (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		if (this.year != other.year) {
			return false;
		}
		return true;
	}

}
