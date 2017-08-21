/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.guideline;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscstEstAlarmColorPK.
 */
@Getter
@Setter
@Embeddable
public class KscstEstAlarmColorPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The guide condition. */
	@Column(name = "GUIDE_CONDITION")
	private Integer guideCondition;

	/**
	 * Instantiates a new kscst est alarm color PK.
	 */
	public KscstEstAlarmColorPK() {
		super();
	}

	/**
	 * Instantiates a new kscst est alarm color PK.
	 *
	 * @param cid
	 *            the cid
	 * @param guideCondition
	 *            the guide condition
	 */
	public KscstEstAlarmColorPK(String cid, Integer guideCondition) {
		this.cid = cid;
		this.guideCondition = guideCondition;
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
		hash += (int) guideCondition;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscstEstAlarmColorPK)) {
			return false;
		}
		KscstEstAlarmColorPK other = (KscstEstAlarmColorPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if (this.guideCondition != other.guideCondition) {
			return false;
		}
		return true;
	}
}
