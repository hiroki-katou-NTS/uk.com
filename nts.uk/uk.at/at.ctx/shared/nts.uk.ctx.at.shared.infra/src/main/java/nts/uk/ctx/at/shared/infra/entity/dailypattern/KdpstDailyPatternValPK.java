/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.dailypattern;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KcvmtContCalendarValPK.
 */
@Setter
@Getter
@Embeddable
public class KdpstDailyPatternValPK implements Serializable {

	 /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The patternCd. */
    @Basic(optional = false)
    @Column(name = "PATTERN_CD")
    private String patternCd;
    
    /** The order number. */
    @Basic(optional = false)
    @Column(name = "DISP_ORDER")
    private Integer dispOrder;
    
    /** The cid. */
    @Basic(optional = false)
    @Column(name = "CID")
	private String cid;
    
    /**
     * Instantiates a new kcvmt work type PK.
     */
    public KdpstDailyPatternValPK(){
    }

	/**
	 * Instantiates a new kcvmt work type PK.
	 * @param patternCd
	 * @param dispOrder
	 * @param cid
	 */
	public KdpstDailyPatternValPK(String patternCd, Integer dispOrder, String cid) {
		this.patternCd = patternCd;
		this.dispOrder = dispOrder;
		this.cid = cid;
	}

    
//	/* (non-Javadoc)
//     * @see java.lang.Object#hashCode()
//     */
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (cid != null ? cid.hashCode() : 0);
//        hash += (nursingCtr != null ? nursingCtr.hashCode() : 0);
//        hash += (orderNumber != null ? orderNumber.hashCode() : 0);
//        return hash;
//    }
//
//    /* (non-Javadoc)
//     * @see java.lang.Object#equals(java.lang.Object)
//     */
//    @Override
//    public boolean equals(Object object) {
//        if (!(object instanceof KnlmtNursingWorkTypePK)) {
//            return false;
//        }
//        KnlmtNursingWorkTypePK other = (KnlmtNursingWorkTypePK) object;
//        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
//            return false;
//        }
//        if ((this.nursingCtr == null && other.nursingCtr != null) || (this.nursingCtr != null
//                && !this.nursingCtr.equals(other.nursingCtr))) {
//            return false;
//        }
//        if ((this.orderNumber == null && other.orderNumber != null) || (this.orderNumber != null
//                && !this.orderNumber.equals(other.orderNumber))) {
//            return false;
//        }
//        return true;
//    }
}
