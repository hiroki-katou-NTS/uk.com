/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.dailypattern;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KdpstDailyPatternVal.
 */
@Setter
@Getter
@Entity
@Table(name = "KSCMT_DAILY_PATTERN_VAL")
public class KdpstDailyPatternVal extends UkJpaEntity implements Serializable{
	
	 /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
	
    /** The kdpst daily pattern val PK. */
    @EmbeddedId
    private KdpstDailyPatternValPK kdpstDailyPatternValPK;
	
    /** The work type set cd. */
    @Basic(optional = false)
    @Column(name = "WORK_TYPE_CD")
    private String workTypeSetCd;
    
    /** The working hours cd. */
    @Basic(optional = false)
    @Column(name = "WORKING_CD")
    private String workingHoursCd;
    
    /** The days. */
    @Basic(optional = false)
    @Column(name = "DAYS")
    private Integer days;
	
    /**
     * Instantiates a new kdpst daily pattern val.
     */
    public KdpstDailyPatternVal(){
    }
    
    /**
     * Instantiates a new kdpst daily pattern val.
     *
     * @param kdpstDailyPatternValPK the kdpst daily pattern val PK
     * @param workTypeSetCd the work type set cd
     * @param workingHoursCd the working hours cd
     * @param days the days
     */
	public KdpstDailyPatternVal(KdpstDailyPatternValPK kdpstDailyPatternValPK, String workTypeSetCd,
			String workingHoursCd, Integer days) {
		this.kdpstDailyPatternValPK = kdpstDailyPatternValPK;
		this.workTypeSetCd = workTypeSetCd;
		this.workingHoursCd = workingHoursCd;
		this.days = days;
	}
	
	/**
	 * Instantiates a new kdpst daily pattern val.
	 *
	 * @param kdpstDailyPatternValPK the kdpst daily pattern val PK
	 */
	public KdpstDailyPatternVal(KdpstDailyPatternValPK kdpstDailyPatternValPK) {
		this.kdpstDailyPatternValPK = kdpstDailyPatternValPK;
	}

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdpstDailyPatternValPK != null ? kdpstDailyPatternValPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // not set
        if (!(object instanceof KdpstDailyPatternVal)) {
            return false;
        }
        KdpstDailyPatternVal other = (KdpstDailyPatternVal) object;
        if ((this.kdpstDailyPatternValPK == null && other.kdpstDailyPatternValPK != null)
                || (this.kdpstDailyPatternValPK != null
                        && !this.kdpstDailyPatternValPK.equals(other.kdpstDailyPatternValPK))) {
            return false;
        }
        return true;
    }
	/*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
	@Override
	protected Object getKey() {
		return this.kdpstDailyPatternValPK;
	}


}
