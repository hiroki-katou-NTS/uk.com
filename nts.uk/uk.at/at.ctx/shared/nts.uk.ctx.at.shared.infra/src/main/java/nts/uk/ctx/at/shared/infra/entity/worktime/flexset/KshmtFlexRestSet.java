/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

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
 * The Class KshmtFlexRestSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLEX_REST_SET")
public class KshmtFlexRestSet extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt flex rest set PK. */
    @EmbeddedId
    protected KshmtFlexRestSetPK kshmtFlexRestSetPK;
    
    /** The is refer rest time. */
    @Basic(optional = false)
    @Column(name = "IS_REFER_REST_TIME")
    private int isReferRestTime;
    
    /** The user private go out rest. */
    @Basic(optional = false)
    @Column(name = "USER_PRIVATE_GO_OUT_REST")
    private int userPrivateGoOutRest;
    
    /** The user asso go out rest. */
    @Basic(optional = false)
    @Column(name = "USER_ASSO_GO_OUT_REST")
    private int userAssoGoOutRest;
    
    /** The fixed rest calc method. */
    @Basic(optional = false)
    @Column(name = "FIXED_REST_CALC_METHOD")
    private int fixedRestCalcMethod;
    
    /** The use stamp. */
    @Basic(optional = false)
    @Column(name = "USE_STAMP")
    private int useStamp;
    
    /** The use stamp calc method. */
    @Basic(optional = false)
    @Column(name = "USE_STAMP_CALC_METHOD")
    private int useStampCalcMethod;
    
    /** The time manager set atr. */
    @Basic(optional = false)
    @Column(name = "TIME_MANAGER_SET_ATR")
    private int timeManagerSetAtr;
    
    /** The calculate method. */
    @Basic(optional = false)
    @Column(name = "CALCULATE_METHOD")
    private int calculateMethod;
    
    /** The use plural work rest time. */
    @Basic(optional = false)
    @Column(name = "USE_PLURAL_WORK_REST_TIME")
    private int usePluralWorkRestTime;
    
    /** The common calculate method. */
    @Basic(optional = false)
    @Column(name = "COMMON_CALCULATE_METHOD")
    private int commonCalculateMethod;

    /**
     * Instantiates a new kshmt flex rest set.
     */
    public KshmtFlexRestSet() {
    }

    /**
     * Instantiates a new kshmt flex rest set.
     *
     * @param kshmtFlexRestSetPK the kshmt flex rest set PK
     */
    public KshmtFlexRestSet(KshmtFlexRestSetPK kshmtFlexRestSetPK) {
        this.kshmtFlexRestSetPK = kshmtFlexRestSetPK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtFlexRestSetPK != null ? kshmtFlexRestSetPK.hashCode() : 0);
        return hash;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexRestSet)) {
			return false;
		}
		KshmtFlexRestSet other = (KshmtFlexRestSet) object;
		if ((this.kshmtFlexRestSetPK == null && other.kshmtFlexRestSetPK != null)
				|| (this.kshmtFlexRestSetPK != null && !this.kshmtFlexRestSetPK.equals(other.kshmtFlexRestSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		 return "entity.KshmtFlexRestSet[ kshmtFlexRestSetPK=" + kshmtFlexRestSetPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtFlexRestSetPK;
	}
    
}
