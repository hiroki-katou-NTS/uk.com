/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class KscmtWsPersonFeeTimePK.
 */

@Getter
@Setter
@Embeddable
public class KscdtScheFeeTimePK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The sid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;
    
    /** The ymd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "YMD")
    private GeneralDate ymd;
    
    /** The no. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO")
    private short no;

    /**
     * Instantiates a new kscmt ws person fee time PK.
     */
    public KscdtScheFeeTimePK() {
    }

    /**
     * Instantiates a new kscmt ws person fee time PK.
     *
     * @param sid the sid
     * @param ymd the ymd
     * @param no the no
     */
    public KscdtScheFeeTimePK(String sid, GeneralDate ymd, short no) {
        this.sid = sid;
        this.ymd = ymd;
        this.no = no;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (ymd != null ? ymd.hashCode() : 0);
        hash += (int) no;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscdtScheFeeTimePK)) {
            return false;
        }
        KscdtScheFeeTimePK other = (KscdtScheFeeTimePK) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        if ((this.ymd == null && other.ymd != null) || (this.ymd != null && !this.ymd.equals(other.ymd))) {
            return false;
        }
        if (this.no != other.no) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtWsPersonFeeTimePK[ sid=" + sid + ", ymd=" + ymd + ", no=" + no + " ]";
    }
    
    
}
