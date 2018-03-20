/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshstComFlexSet.
 */
@Setter
@Getter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KshstFlexSet extends UkJpaEntity {
	
    /** The exclus ver. */
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    
    /** The stat jan time. */
    @Column(name = "STAT_JAN_TIME")
    private int statJanTime;
    
    /** The stat feb time. */
    @Column(name = "STAT_FEB_TIME")
    private int statFebTime;
    
    /** The stat mar time. */
    @Column(name = "STAT_MAR_TIME")
    private int statMarTime;
    
    /** The stat apr time. */
    @Basic(optional = false)
    
    @Column(name = "STAT_APR_TIME")
    private int statAprTime;
    
    /** The stat may time. */
    @Column(name = "STAT_MAY_TIME")
    private int statMayTime;
    
    /** The stat jun time. */
    @Column(name = "STAT_JUN_TIME")
    private int statJunTime;
    
    /** The stat jul time. */
    @Column(name = "STAT_JUL_TIME")
    private int statJulTime;
    
    /** The stat aug time. */
    @Column(name = "STAT_AUG_TIME")
    private int statAugTime;
    
    /** The stat sep time. */
    @Column(name = "STAT_SEP_TIME")
    private int statSepTime;
    
    /** The stat oct time. */
    @Column(name = "STAT_OCT_TIME")
    private int statOctTime;
    
    /** The stat nov time. */
    @Column(name = "STAT_NOV_TIME")
    private int statNovTime;
    
    /** The stat dec time. */
    @Column(name = "STAT_DEC_TIME")
    private int statDecTime;
    
    /** The spec jan time. */
    @Column(name = "SPEC_JAN_TIME")
    private int specJanTime;
    
    /** The spec feb time. */
    @Column(name = "SPEC_FEB_TIME")
    private int specFebTime;
    
    /** The spec mar time. */
    @Column(name = "SPEC_MAR_TIME")
    private int specMarTime;
    
    /** The spec apr time. */
    @Column(name = "SPEC_APR_TIME")
    private int specAprTime;
    
    /** The spec may time. */
    @Column(name = "SPEC_MAY_TIME")
    private int specMayTime;
    
    /** The spec jun time. */
    @Column(name = "SPEC_JUN_TIME")
    private int specJunTime;
    
    /** The spec jul time. */
    @Column(name = "SPEC_JUL_TIME")
    private int specJulTime;
    
    /** The spec aug time. */
    @Column(name = "SPEC_AUG_TIME")
    private int specAugTime;
    
    /** The spec sep time. */
    @Column(name = "SPEC_SEP_TIME")
    private int specSepTime;
    
    /** The spec oct time. */
    @Column(name = "SPEC_OCT_TIME")
    private int specOctTime;
    
    /** The spec nov time. */
    @Column(name = "SPEC_NOV_TIME")
    private int specNovTime;
    
	/** The spec dec time. */
	@Column(name = "SPEC_DEC_TIME")
	private int specDecTime;

}
