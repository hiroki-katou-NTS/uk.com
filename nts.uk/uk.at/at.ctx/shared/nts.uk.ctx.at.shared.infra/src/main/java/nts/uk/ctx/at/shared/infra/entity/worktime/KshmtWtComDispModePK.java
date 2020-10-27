/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtWtComDispModePK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWtComDispModePK implements Serializable {

	/** The Constant serialVersionUID. */
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The worktime cd. */
	@Column(name = "WORKTIME_CD")
	private String worktimeCd;

	/**
	 * Instantiates a new kshmt worktime disp mode PK.
	 */
	public KshmtWtComDispModePK() {
	}

	/**
	 * Instantiates a new kshmt worktime disp mode PK.
	 *
	 * @param cid
	 *            the cid
	 * @param worktimeCd
	 *            the worktime cd
	 */
	public KshmtWtComDispModePK(String cid, String worktimeCd) {
		super();
		this.cid = cid;
		this.worktimeCd = worktimeCd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KshmtWtComDispModePK)) {
			return false;
		}
		KshmtWtComDispModePK castOther = (KshmtWtComDispModePK) other;
		return this.cid.equals(castOther.cid) && this.worktimeCd.equals(castOther.worktimeCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.worktimeCd.hashCode();

		return hash;
	}
}