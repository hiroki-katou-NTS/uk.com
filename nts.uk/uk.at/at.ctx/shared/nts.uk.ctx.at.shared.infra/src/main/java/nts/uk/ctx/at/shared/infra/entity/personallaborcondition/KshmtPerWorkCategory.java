/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtPerWorkCategory.
 */

@Getter
@Setter
@Entity
@Table(name = "KSHMT_PER_WORK_CATEGORY")
public class KshmtPerWorkCategory extends ContractUkJpaEntity implements Serializable {

	 /** The Constant DEFAULT_TIME. */
    public static final int DEFAULT_TIME = 0;
    
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt per work category PK. */
    @EmbeddedId
    protected KshmtPerWorkCategoryPK kshmtPerWorkCategoryPK;
    
    /** The worktype cd. */
    @Basic(optional = false)
    @Column(name = "WORKTYPE_CD")
    private String worktypeCd;
    
    /** The worktime cd. */
    @Column(name = "WORKTIME_CD")
    private String worktimeCd;
    
    /** The use atr 1. */
    @Column(name = "USE_ATR_1")
    private Integer useAtr1;
    
    /** The use atr 2. */
    @Column(name = "USE_ATR_2")
    private Integer useAtr2;
    
    /** The cnt 1. */
    @Column(name = "CNT_1")
    private Integer cnt1;
    
    /** The cnt 2. */
    @Column(name = "CNT_2")
    private Integer cnt2;
    
    /** The end 1. */
    @Column(name = "END_1")
    private Integer end1;
    
    /** The end 2. */
    @Column(name = "END_2")
    private Integer end2;
    
    /** The start 1. */
    @Column(name = "START_1")
    private Integer start1;
    
    /** The start 2. */
    @Column(name = "START_2")
    private Integer start2;
    
    /**
     * Default time zone.
     */
    public void defaultTimeZone(){
    	this.useAtr1 = UseAtr.NOTUSE.value;
    	this.cnt1 = DEFAULT_TIME;
    	this.start1 = DEFAULT_TIME;
    	this.end1 = DEFAULT_TIME;
    	this.useAtr2 = UseAtr.NOTUSE.value;
    	this.cnt2 = DEFAULT_TIME;
    	this.start2 = DEFAULT_TIME;
    	this.end2 = DEFAULT_TIME;
    }
    

    /**
     * Instantiates a new kshmt per work category.
     */
    public KshmtPerWorkCategory() {
    }

    
    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtPerWorkCategoryPK != null ? kshmtPerWorkCategoryPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
		if (!(object instanceof KshmtPerWorkCategory)) {
			return false;
		}
		KshmtPerWorkCategory other = (KshmtPerWorkCategory) object;
		if ((this.kshmtPerWorkCategoryPK == null && other.kshmtPerWorkCategoryPK != null)
				|| (this.kshmtPerWorkCategoryPK != null
						&& !this.kshmtPerWorkCategoryPK.equals(other.kshmtPerWorkCategoryPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtPerWorkCategory[ kshmtPerWorkCategoryPK=" + kshmtPerWorkCategoryPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
    
}
