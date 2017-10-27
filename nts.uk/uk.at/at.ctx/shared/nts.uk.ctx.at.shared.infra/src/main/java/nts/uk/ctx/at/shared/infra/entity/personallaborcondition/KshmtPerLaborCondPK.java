/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtPerLaborCondPK.
 */
@Embeddable
@Getter
@Setter
public class KshmtPerLaborCondPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The sid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;
    
    /** The start ymd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_YMD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startYmd;
    
    /** The end ymd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_YMD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endYmd;

    /**
     * Instantiates a new kshmt per labor cond PK.
     */
    public KshmtPerLaborCondPK() {
    }

    /**
     * Instantiates a new kshmt per labor cond PK.
     *
     * @param sid the sid
     * @param startYmd the start ymd
     * @param endYmd the end ymd
     */
    public KshmtPerLaborCondPK(String sid, Date startYmd, Date endYmd) {
        this.sid = sid;
        this.startYmd = startYmd;
        this.endYmd = endYmd;
    }

    /**
     * Gets the sid.
     *
     * @return the sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * Sets the sid.
     *
     * @param sid the new sid
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * Gets the start ymd.
     *
     * @return the start ymd
     */
    public Date getStartYmd() {
        return startYmd;
    }

    /**
     * Sets the start ymd.
     *
     * @param startYmd the new start ymd
     */
    public void setStartYmd(Date startYmd) {
        this.startYmd = startYmd;
    }

    /**
     * Gets the end ymd.
     *
     * @return the end ymd
     */
    public Date getEndYmd() {
        return endYmd;
    }

    /**
     * Sets the end ymd.
     *
     * @param endYmd the new end ymd
     */
    public void setEndYmd(Date endYmd) {
        this.endYmd = endYmd;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (startYmd != null ? startYmd.hashCode() : 0);
        hash += (endYmd != null ? endYmd.hashCode() : 0);
        return hash;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtPerLaborCondPK)) {
			return false;
		}
		KshmtPerLaborCondPK other = (KshmtPerLaborCondPK) object;
		if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		if ((this.startYmd == null && other.startYmd != null)
				|| (this.startYmd != null && !this.startYmd.equals(other.startYmd))) {
			return false;
		}
		if ((this.endYmd == null && other.endYmd != null)
				|| (this.endYmd != null && !this.endYmd.equals(other.endYmd))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtPerLaborCondPK[ sid=" + sid + ", startYmd=" + startYmd + ", endYmd=" + endYmd + " ]";
    }
    
}
