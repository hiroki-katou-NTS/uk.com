/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class KscmtChildCareSchedulePK.
 */
@Getter
@Setter
@Embeddable
public class KscmtChildCareSchedulePK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;
    
    /** The date. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "YMD")
	public GeneralDate ymd;
    
    /** The child care number. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILD_CARE_NUMBER")
    private int childCareNumber;

    public KscmtChildCareSchedulePK() {
    }

    public KscmtChildCareSchedulePK(String sid, GeneralDate ymd, int childCareNumber) {
        this.sid = sid;
        this.ymd = ymd;
        this.childCareNumber = childCareNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (ymd != null ? ymd.hashCode() : 0);
        hash += (int) childCareNumber;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscmtChildCareSchedulePK)) {
            return false;
        }
        KscmtChildCareSchedulePK other = (KscmtChildCareSchedulePK) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        if ((this.ymd == null && other.ymd != null) || (this.ymd != null && !this.ymd.equals(other.ymd))) {
            return false;
        }
        if (this.childCareNumber != other.childCareNumber) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KscmtChildCareSchedulePK[ sid=" + sid + ", ymd=" + ymd + ", childCareNumber=" + childCareNumber + " ]";
    }
    
}
