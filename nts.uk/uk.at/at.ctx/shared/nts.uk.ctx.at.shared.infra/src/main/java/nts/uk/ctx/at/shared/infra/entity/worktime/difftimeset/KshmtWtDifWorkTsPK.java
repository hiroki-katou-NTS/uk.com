/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtWtDifWorkTsPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWtDifWorkTsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The worktime cd. */
	@Column(name = "WORKTIME_CD")
	private String worktimeCd;

	/** The am pm atr. */
	@Column(name = "AM_PM_ATR")
	private int amPmAtr;

	/** The time frame no. */
	@Column(name = "TIME_FRAME_NO")
	private int timeFrameNo;

	/**
	 * Instantiates a new kshmt dt work time set PK.
	 */
	public KshmtWtDifWorkTsPK() {
		super();
	}

	/**
	 * Instantiates a new kshmt dt work time set PK.
	 *
	 * @param cid
	 *            the cid
	 * @param worktimeCd
	 *            the worktime cd
	 * @param amPmAtr
	 *            the am pm atr
	 * @param timeFrameNo
	 *            the time frame no
	 */
	public KshmtWtDifWorkTsPK(String cid, String worktimeCd, int amPmAtr, int timeFrameNo) {
		super();
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.amPmAtr = amPmAtr;
		this.timeFrameNo = timeFrameNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amPmAtr;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + timeFrameNo;
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
		KshmtWtDifWorkTsPK other = (KshmtWtDifWorkTsPK) obj;
		if (amPmAtr != other.amPmAtr)
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (timeFrameNo != other.timeFrameNo)
			return false;
		if (worktimeCd == null) {
			if (other.worktimeCd != null)
				return false;
		} else if (!worktimeCd.equals(other.worktimeCd))
			return false;
		return true;
	}

}
