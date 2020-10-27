/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtWtComGooutRoundPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWtComGooutRoundPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The worktime cd. */
	@Column(name = "WORKTIME_CD")
	private String worktimeCd;

	/** The work form atr. */
	@Column(name = "WORK_FORM_ATR")
	private int workFormAtr;

	/** The worktime set method. */
	@Column(name = "WORKTIME_SET_METHOD")
	private int worktimeSetMethod;

	/** The rounding time type. */
	@Column(name = "ROUNDING_TIME_TYPE")
	private int roundingTimeType;

	/**
	 * Instantiates a new kshmt special round out PK.
	 */
	public KshmtWtComGooutRoundPK() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt special round out PK.
	 *
	 * @param cid the cid
	 * @param worktimeCd the worktime cd
	 * @param workFormAtr the work form atr
	 * @param worktimeSetMethod the worktime set method
	 * @param roundingTimeType the rounding time type
	 */
	public KshmtWtComGooutRoundPK(String cid, String worktimeCd, int workFormAtr, int worktimeSetMethod, int roundingTimeType) {
		super();
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.workFormAtr = workFormAtr;
		this.worktimeSetMethod = worktimeSetMethod;
		this.roundingTimeType = roundingTimeType;
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
		hash += (worktimeCd != null ? worktimeCd.hashCode() : 0);
		hash += (int) workFormAtr;
		hash += (int) worktimeSetMethod;
		hash += (int) roundingTimeType;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtComGooutRoundPK)) {
			return false;
		}
		KshmtWtComGooutRoundPK other = (KshmtWtComGooutRoundPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.worktimeCd == null && other.worktimeCd != null)
				|| (this.worktimeCd != null && !this.worktimeCd.equals(other.worktimeCd))) {
			return false;
		}
		if (this.workFormAtr != other.workFormAtr) {
			return false;
		}
		if (this.worktimeSetMethod != other.worktimeSetMethod) {
			return false;
		}
		if (this.roundingTimeType != other.roundingTimeType) {
			return false;
		}
		return true;
	}
}
