/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.automaticcalculation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtAutoOtCalSetPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtAutoOtCalSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The wkpid. */
	@Column(name = "WKPID")
	private short wkpid;

	/** The jobid. */
	@Column(name = "JOBID")
	private short jobid;

	/** The auto cal atr. */
	@Column(name = "AUTO_CAL_ATR")
	private short autoCalAtr;

	/**
	 * Instantiates a new kshmt auto ot cal set PK.
	 */
	public KshmtAutoOtCalSetPK() {
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
		hash += (int) wkpid;
		hash += (int) jobid;
		hash += (int) autoCalAtr;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtAutoOtCalSetPK)) {
			return false;
		}
		KshmtAutoOtCalSetPK other = (KshmtAutoOtCalSetPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if (this.wkpid != other.wkpid) {
			return false;
		}
		if (this.jobid != other.jobid) {
			return false;
		}
		if (this.autoCalAtr != other.autoCalAtr) {
			return false;
		}
		return true;
	}

}
