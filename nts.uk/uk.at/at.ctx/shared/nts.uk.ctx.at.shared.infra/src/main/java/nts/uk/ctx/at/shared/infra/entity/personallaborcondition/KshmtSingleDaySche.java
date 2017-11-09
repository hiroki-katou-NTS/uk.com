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
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtSingleDaySche.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_SINGLE_DAY_SCHE")
public class KshmtSingleDaySche extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The Constant DEFAULT_TIME. */
    public static final int DEFAULT_TIME = 0;
    /** The kshmt single day sche PK. */
    @EmbeddedId
    protected KshmtSingleDaySchePK kshmtSingleDaySchePK;
    
    /** The worktype cd. */
    @Basic(optional = false)
    @NotNull
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
     * Instantiates a new kshmt single day sche.
     */
    public KshmtSingleDaySche() {
    }

    /**
     * Instantiates a new kshmt single day sche.
     *
     * @param kshmtSingleDaySchePK the kshmt single day sche PK
     */
    public KshmtSingleDaySche(KshmtSingleDaySchePK kshmtSingleDaySchePK) {
        this.kshmtSingleDaySchePK = kshmtSingleDaySchePK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtSingleDaySchePK != null ? kshmtSingleDaySchePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtSingleDaySche)) {
			return false;
		}
		KshmtSingleDaySche other = (KshmtSingleDaySche) object;
		if ((this.kshmtSingleDaySchePK == null && other.kshmtSingleDaySchePK != null)
				|| (this.kshmtSingleDaySchePK != null
						&& !this.kshmtSingleDaySchePK.equals(other.kshmtSingleDaySchePK))) {
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
		return "entity.KshmtSingleDaySche[ kshmtSingleDaySchePK=" + kshmtSingleDaySchePK + " ]";
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtSingleDaySchePK;
	}
    
}
