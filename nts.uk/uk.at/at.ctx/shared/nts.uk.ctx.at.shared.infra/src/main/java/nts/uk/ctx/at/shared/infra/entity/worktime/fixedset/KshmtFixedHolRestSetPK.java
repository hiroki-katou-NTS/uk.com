/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.fixedset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtFixedHolRestSetPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtFixedHolRestSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The worktime cd. */
	@Column(name = "WORKTIME_CD")
	private String worktimeCd;
	
	/** The period no. */
    @Column(name = "PERIOD_NO")
    private int periodNo;

	/**
	 * Instantiates a new kshmt fixed hol rest set PK.
	 */
	public KshmtFixedHolRestSetPK() {
		super();
	}

	/**
	 * Instantiates a new kshmt fixed hol rest set PK.
	 *
	 * @param cid the cid
	 * @param worktimeCd the worktime cd
	 * @param periodNo the period no
	 */
	public KshmtFixedHolRestSetPK(String cid, String worktimeCd, int periodNo) {
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.periodNo = periodNo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + periodNo;
		result = prime * result + ((worktimeCd == null) ? 0 : worktimeCd.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KshmtFixedHolRestSetPK))
			return false;
		KshmtFixedHolRestSetPK other = (KshmtFixedHolRestSetPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (periodNo != other.periodNo)
			return false;
		if (worktimeCd == null) {
			if (other.worktimeCd != null)
				return false;
		} else if (!worktimeCd.equals(other.worktimeCd))
			return false;
		return true;
	}
	
}
