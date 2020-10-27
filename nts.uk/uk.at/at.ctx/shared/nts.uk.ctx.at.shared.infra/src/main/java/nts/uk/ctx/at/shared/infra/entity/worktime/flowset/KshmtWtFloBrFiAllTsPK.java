/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flowset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtWtFloBrFiAllTsPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWtFloBrFiAllTsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The worktime cd. */
	@Column(name = "WORKTIME_CD")
	private String worktimeCd;

	/** The resttime atr. */
	@Column(name = "RESTTIME_ATR")
	private int resttimeAtr;
	
	/** The period no. */
	@Column(name = "PERIOD_NO")
	private int periodNo;

	/**
	 * Instantiates a new kshmt flow fixed rt set PK.
	 */
	public KshmtWtFloBrFiAllTsPK() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + periodNo;
		result = prime * result + resttimeAtr;
		result = prime * result + ((worktimeCd == null) ? 0 : worktimeCd.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KshmtWtFloBrFiAllTsPK other = (KshmtWtFloBrFiAllTsPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (periodNo != other.periodNo)
			return false;
		if (resttimeAtr != other.resttimeAtr)
			return false;
		if (worktimeCd == null) {
			if (other.worktimeCd != null)
				return false;
		} else if (!worktimeCd.equals(other.worktimeCd))
			return false;
		return true;
	}

}
