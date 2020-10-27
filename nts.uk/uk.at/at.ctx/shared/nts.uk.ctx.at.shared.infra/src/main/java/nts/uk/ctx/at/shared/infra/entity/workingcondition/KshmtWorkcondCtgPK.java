/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtWorkcondCtgPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWorkcondCtgPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The history id. */
	@Column(name = "HIST_ID")
	private String historyId;

	/** The per work cat atr. */
	@Column(name = "PER_WORK_CAT_ATR")
	private int perWorkCatAtr;

	/**
	 * Instantiates a new kshmt per work cat PK.
	 */
	public KshmtWorkcondCtgPK() {
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
		hash += (historyId != null ? historyId.hashCode() : 0);
		hash += (int) perWorkCatAtr;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkcondCtgPK)) {
			return false;
		}
		KshmtWorkcondCtgPK other = (KshmtWorkcondCtgPK) object;
		if ((this.historyId == null && other.historyId != null)
				|| (this.historyId != null && !this.historyId.equals(other.historyId))) {
			return false;
		}
		if (this.perWorkCatAtr != other.perWorkCatAtr) {
			return false;
		}
		return true;
	}

	public KshmtWorkcondCtgPK(String historyId, int perWorkCatAtr) {
		super();
		this.historyId = historyId;
		this.perWorkCatAtr = perWorkCatAtr;
	}

}
