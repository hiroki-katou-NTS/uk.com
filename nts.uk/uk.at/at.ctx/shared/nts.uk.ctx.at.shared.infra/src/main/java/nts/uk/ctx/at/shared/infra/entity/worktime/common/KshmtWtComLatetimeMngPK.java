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
 * The Class KshmtWtComLatetimeMngPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWtComLatetimeMngPK implements Serializable {

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

	/**
	 * Instantiates a new kshmt late early set PK.
	 */
	public KshmtWtComLatetimeMngPK() {
		super();
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
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtComLatetimeMngPK)) {
			return false;
		}
		KshmtWtComLatetimeMngPK other = (KshmtWtComLatetimeMngPK) object;
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
		return true;
	}
}
