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
 * The Class KshmtWorkcondCtgegoryPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWorkcondCtgegoryPK implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The sid. */
	@Basic(optional = false)
    @Column(name = "SID")
    private String sid;
    
    /** The work category atr. */
    @Basic(optional = false)
    @Column(name = "WORK_CATEGORY_ATR")
    private int workCategoryAtr;
    
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
     * Instantiates a new kshmt per work category PK.
     */
    public KshmtWorkcondCtgegoryPK() {
    }

  
    /**
     * Instantiates a new kshmt per work category PK.
     *
     * @param sid the sid
     * @param workCategoryAtr the work category atr
     * @param startYmd the start ymd
     * @param endYmd the end ymd
     */
    public KshmtWorkcondCtgegoryPK(String sid, int workCategoryAtr, GeneralDate startYmd,
			GeneralDate endYmd) {
		super();
		this.sid = sid;
		this.workCategoryAtr = workCategoryAtr;
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
        hash += (int) workCategoryAtr;
        hash += (startYmd != null ? startYmd.hashCode() : 0);
        hash += (endYmd != null ? endYmd.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtWorkcondCtgegoryPK)) {
			return false;
		}
		KshmtWorkcondCtgegoryPK other = (KshmtWorkcondCtgegoryPK) object;
		if ((this.sid == null && other.sid != null)
				|| (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		if (this.workCategoryAtr != other.workCategoryAtr) {
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
		return "entity.KshmtWorkcondCtgegoryPK[ sid=" + sid + ", workCategoryAtr=" + workCategoryAtr
				+ ", startYmd=" + startYmd + ", endYmd=" + endYmd + " ]";
	}
    
}
