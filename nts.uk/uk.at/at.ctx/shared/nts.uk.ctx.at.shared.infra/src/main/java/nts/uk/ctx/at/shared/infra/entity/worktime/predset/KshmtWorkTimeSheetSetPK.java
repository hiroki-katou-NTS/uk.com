/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.predset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class KshmtWorkTimeSheetSetPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	private String cid;

	@Column(name = "WORKTIME_CD")
	private String worktimeCd;

	@Column(name = "TIME_NUMBER_CNT")
	private Integer timeNumberCnt;

	public KshmtWorkTimeSheetSetPK() {
	}
	
	public KshmtWorkTimeSheetSetPK(String cid, String worktimeCd, Integer timeNumberCnt) {
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.timeNumberCnt = timeNumberCnt;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (worktimeCd != null ? worktimeCd.hashCode() : 0);
		hash += (int) timeNumberCnt;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkTimeSheetSetPK)) {
			return false;
		}
		KshmtWorkTimeSheetSetPK other = (KshmtWorkTimeSheetSetPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.worktimeCd == null && other.worktimeCd != null)
				|| (this.worktimeCd != null && !this.worktimeCd.equals(other.worktimeCd))) {
			return false;
		}
		if (this.timeNumberCnt != other.timeNumberCnt) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "javaapplication1.KshmtWorkTimeSheetSetPK[ cid=" + cid + ", worktimeCd=" + worktimeCd
				+ ", timeNumberCnt=" + timeNumberCnt + " ]";
	}

}
