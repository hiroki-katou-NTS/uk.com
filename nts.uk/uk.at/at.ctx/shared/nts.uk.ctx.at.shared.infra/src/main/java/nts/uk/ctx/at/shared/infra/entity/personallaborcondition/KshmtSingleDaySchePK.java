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
 * The Class KshmtSingleDaySchePK.
 */
@Embeddable
@Getter
@Setter
public class KshmtSingleDaySchePK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The sid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;
    
    /** The pers work atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERS_WORK_ATR")
    private short persWorkAtr;
    
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
     * Instantiates a new kshmt single day sche PK.
     */
    public KshmtSingleDaySchePK() {
    }

    /**
     * Instantiates a new kshmt single day sche PK.
     *
     * @param sid the sid
     * @param persWorkAtr the pers work atr
     * @param startYmd the start ymd
     * @param endYmd the end ymd
     */
    public KshmtSingleDaySchePK(String sid, short persWorkAtr, Date startYmd, Date endYmd) {
        this.sid = sid;
        this.persWorkAtr = persWorkAtr;
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
     * Gets the pers work atr.
     *
     * @return the pers work atr
     */
    public short getPersWorkAtr() {
        return persWorkAtr;
    }

    /**
     * Sets the pers work atr.
     *
     * @param persWorkAtr the new pers work atr
     */
    public void setPersWorkAtr(short persWorkAtr) {
        this.persWorkAtr = persWorkAtr;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (sid != null ? sid.hashCode() : 0);
		hash += (int) persWorkAtr;
		hash += (startYmd != null ? startYmd.hashCode() : 0);
		hash += (endYmd != null ? endYmd.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtSingleDaySchePK)) {
			return false;
		}
		KshmtSingleDaySchePK other = (KshmtSingleDaySchePK) object;
		if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		if (this.persWorkAtr != other.persWorkAtr) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtSingleDaySchePK[ sid=" + sid + ", persWorkAtr=" + persWorkAtr + ", startYmd=" + startYmd
				+ ", endYmd=" + endYmd + " ]";
	}

}
