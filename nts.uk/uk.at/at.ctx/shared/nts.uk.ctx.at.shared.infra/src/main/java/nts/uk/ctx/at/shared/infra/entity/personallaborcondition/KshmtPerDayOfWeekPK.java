/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class KshmtPerDayOfWeekPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtPerDayOfWeekPK implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The sid. */
	@Basic(optional = false)
    @Column(name = "SID")
    private String sid;
    
    /** The day of week atr. */
    @Basic(optional = false)
    @Column(name = "DAY_OF_WEEK_ATR")
    private int dayOfWeekAtr;
    
    /** The start ymd. */
    @Basic(optional = false)
    @Column(name = "START_YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate startYmd;
    
    /** The end ymd. */
    @Basic(optional = false)
    @Column(name = "END_YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endYmd;

    /**
     * Instantiates a new kshmt per day of week PK.
     */
    public KshmtPerDayOfWeekPK() {
    }
    
    /**
     * Instantiates a new kshmt per day of week PK.
     *
     * @param sid the sid
     * @param dayOfWeekAtr the day of week atr
     * @param startYmd the start ymd
     * @param endYmd the end ymd
     */
    public KshmtPerDayOfWeekPK(String sid, int dayOfWeekAtr, GeneralDate startYmd,
			GeneralDate endYmd) {
		super();
		this.sid = sid;
		this.dayOfWeekAtr = dayOfWeekAtr;
		this.startYmd = startYmd;
		this.endYmd = endYmd;
	}


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (int) dayOfWeekAtr;
        hash += (startYmd != null ? startYmd.hashCode() : 0);
        hash += (endYmd != null ? endYmd.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtPerDayOfWeekPK)) {
            return false;
        }
        KshmtPerDayOfWeekPK other = (KshmtPerDayOfWeekPK) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        if (this.dayOfWeekAtr != other.dayOfWeekAtr) {
            return false;
        }
        if ((this.startYmd == null && other.startYmd != null) || (this.startYmd != null && !this.startYmd.equals(other.startYmd))) {
            return false;
        }
        if ((this.endYmd == null && other.endYmd != null) || (this.endYmd != null && !this.endYmd.equals(other.endYmd))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
		return "entity.KshmtPerDayOfWeekPK[ sid=" + sid + ", dayOfWeekAtr=" + dayOfWeekAtr
				+ ", startYmd=" + startYmd + ", endYmd=" + endYmd + " ]";
	}

}
