/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.dailypattern;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class kcvstContCalenderVal.
 */

/**
 * Sets the days.
 *
 * @param days the new days
 */
@Setter
@Getter
@Entity
@Table(name = "KDPST_DAILY_PATTERN_VAL")
public class KdpstDailyPatternVal extends UkJpaEntity implements Serializable{
	
	 /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
	
    /** The knlmtNursingWorkTypePK. */
    @EmbeddedId
    private KdpstDailyPatternValPK kcvmtContCalendarValPK;
	
    /** The workTypeSetCd. */
    @Basic(optional = false)
    @Column(name = "WORK_TYPE_CD")
    private String workTypeSetCd;
    
    /** The workingHoursCd. */
    @Basic(optional = false)
    @Column(name = "WORKING_CD")
    private String workingHoursCd;
    
    /** The days number. */
    @Basic(optional = false)
    @Column(name = "DAYS")
    private Integer days;
	
    /**
     * Instantiates a new kcvmt work type.
     */
    public KdpstDailyPatternVal(){
    }
    
    /**
     * Instantiates a new kcvmt cont calendar work type.
     *
     * @param kcvmtContCalendarValPK the kcvmt cont calendar val PK
     * @param workTypeSetCd the work type set cd
     * @param workingHoursCd the working hours cd
     * @param days the days
     */
	public KdpstDailyPatternVal(KdpstDailyPatternValPK kcvmtContCalendarValPK, String workTypeSetCd,
			String workingHoursCd, Integer days) {
		super();
		this.kcvmtContCalendarValPK = kcvmtContCalendarValPK;
		this.workTypeSetCd = workTypeSetCd;
		this.workingHoursCd = workingHoursCd;
		this.days = days;
	}
	
	/**
	 * Instantiates a new kcvmt cont work type.
	 *
	 * @param kcvmtContCalendarValPK the kcvmt cont calendar val PK
	 */
	public KdpstDailyPatternVal(KdpstDailyPatternValPK kcvmtContCalendarValPK) {
		this.kcvmtContCalendarValPK = kcvmtContCalendarValPK;
	}

    /* (non-Javadoc)
//     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
//     */
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (knlmtNursingWorkTypePK != null ? knlmtNursingWorkTypePK.hashCode() : 0);
//        return hash;
//    }
//
//    /* (non-Javadoc)
//     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
//     */
//    @Override
//    public boolean equals(Object object) {
//        // not set
//        if (!(object instanceof KnlmtNursingWorkType)) {
//            return false;
//        }
//        KnlmtNursingWorkType other = (KnlmtNursingWorkType) object;
//        if ((this.knlmtNursingWorkTypePK == null && other.knlmtNursingWorkTypePK != null)
//                || (this.knlmtNursingWorkTypePK != null
//                        && !this.knlmtNursingWorkTypePK.equals(other.knlmtNursingWorkTypePK))) {
//            return false;
//        }
//        return true;
//    }
	/*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
	@Override
	protected Object getKey() {
		return this.kcvmtContCalendarValPK;
	}


}
