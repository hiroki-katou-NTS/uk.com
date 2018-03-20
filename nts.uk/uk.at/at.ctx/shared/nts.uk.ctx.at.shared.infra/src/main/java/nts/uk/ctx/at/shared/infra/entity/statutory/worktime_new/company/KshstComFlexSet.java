/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company;

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
 * The Class KshstComFlexSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_COM_FLEX_SET")
public class KshstComFlexSet extends UkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst com flex set PK. */
    @EmbeddedId
    protected KshstComFlexSetPK kshstComFlexSetPK;
    
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
    

    /**
     * Instantiates a new kshst com flex set.
     */
    public KshstComFlexSet() {
    	super();
    }


    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstComFlexSetPK != null ? kshstComFlexSetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshstComFlexSet)) {
            return false;
        }
        KshstComFlexSet other = (KshstComFlexSet) object;
        if ((this.kshstComFlexSetPK == null && other.kshstComFlexSetPK != null) || (this.kshstComFlexSetPK != null && !this.kshstComFlexSetPK.equals(other.kshstComFlexSetPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstComFlexSetPK;
	}

}
