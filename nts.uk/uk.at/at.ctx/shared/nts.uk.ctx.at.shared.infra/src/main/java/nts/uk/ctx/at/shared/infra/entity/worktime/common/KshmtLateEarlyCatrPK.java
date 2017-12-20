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

@Embeddable
@Getter
@Setter
public class KshmtLateEarlyCatrPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	private String cid;

	@Column(name = "WORKTIME_CD")
	private String worktimeCd;

	@Column(name = "WORK_FORM_ATR")
	private Integer workFormAtr;

	@Column(name = "LATE_EARLY_ATR")
	private Integer lateEarlyAtr;

	public KshmtLateEarlyCatrPK() {
	}
	
	public KshmtLateEarlyCatrPK(String cid, String worktimeCd, Integer workFormAtr, Integer lateEarlyAtr) {
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.workFormAtr = workFormAtr;
		this.lateEarlyAtr = lateEarlyAtr;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (worktimeCd != null ? worktimeCd.hashCode() : 0);
		hash += (int) workFormAtr;
		hash += (int) lateEarlyAtr;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtLateEarlyCatrPK)) {
			return false;
		}
		KshmtLateEarlyCatrPK other = (KshmtLateEarlyCatrPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.worktimeCd == null && other.worktimeCd != null)
				|| (this.worktimeCd != null && !this.worktimeCd.equals(other.worktimeCd))) {
			return false;
		}
		if (this.workFormAtr != other.workFormAtr) {
			return false;
		}
		if (this.lateEarlyAtr != other.lateEarlyAtr) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "javaapplication1.KshmtLateEarlyCatrPK[ cid=" + cid + ", worktimeCd=" + worktimeCd + ", workFormAtr="
				+ workFormAtr + ", lateEarlyAtr=" + lateEarlyAtr + " ]";
	}

}
