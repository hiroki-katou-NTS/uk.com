/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateTimeToDBConverter;

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
    private int persWorkAtr;
    
    /** The start ymd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_YMD")
    @Convert(converter = GeneralDateTimeToDBConverter.class)
    private Date startYmd;
    
    /** The end ymd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_YMD")
    @Convert(converter = GeneralDateTimeToDBConverter.class)
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
    public KshmtSingleDaySchePK(String sid, int persWorkAtr, Date startYmd, Date endYmd) {
        this.sid = sid;
        this.persWorkAtr = persWorkAtr;
        this.startYmd = startYmd;
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
