/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmpstMonthPatternPK.
 */

@Getter
@Setter
@Embeddable
public class KmpstMonthPatternPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The pattern cd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PATTERN_CD")
    private String patternCd;

    public KmpstMonthPatternPK() {
    }

    public KmpstMonthPatternPK(String cid, String patternCd) {
        this.cid = cid;
        this.patternCd = patternCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (patternCd != null ? patternCd.hashCode() : 0);
        return hash;
    }

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KmpstMonthPatternPK)) {
			return false;
		}
		KmpstMonthPatternPK other = (KmpstMonthPatternPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.patternCd == null && other.patternCd != null)
				|| (this.patternCd != null && !this.patternCd.equals(other.patternCd))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.KmpstMonthPatternPK[ cid=" + cid + ", patternCd=" + patternCd + " ]";
	}
    
}
