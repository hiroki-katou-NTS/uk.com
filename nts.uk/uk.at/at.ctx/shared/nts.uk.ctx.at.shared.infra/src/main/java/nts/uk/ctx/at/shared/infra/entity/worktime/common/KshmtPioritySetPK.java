/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtPioritySetPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtPioritySetPK implements Serializable {

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

	/** The work time set method. */
	@Column(name = "WORKTIME_SET_METHOD")
	private int workTimeSetMethod;

	/** The piority atr. */
	@Column(name = "PIORITY_ATR")
	private int piorityAtr;

	/** The stamp atr. */
	@Column(name = "STAMP_ATR")
	private int stampAtr;

	/**
	 * Instantiates a new kshmt piority set PK.
	 */
	public KshmtPioritySetPK() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + piorityAtr;
		result = prime * result + stampAtr;
		result = prime * result + workFormAtr;
		result = prime * result + workTimeSetMethod;
		result = prime * result + ((worktimeCd == null) ? 0 : worktimeCd.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KshmtPioritySetPK other = (KshmtPioritySetPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (piorityAtr != other.piorityAtr)
			return false;
		if (stampAtr != other.stampAtr)
			return false;
		if (workFormAtr != other.workFormAtr)
			return false;
		if (workTimeSetMethod != other.workTimeSetMethod)
			return false;
		if (worktimeCd == null) {
			if (other.worktimeCd != null)
				return false;
		} else if (!worktimeCd.equals(other.worktimeCd))
			return false;
		return true;
	}

}
