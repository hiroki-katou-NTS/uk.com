/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.entity.mailnoticeset.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class SevstUseContactSet.
 */
@Entity
@Table(name = "SEVST_USE_CONTACT_SET")
@Getter
@Setter
public class SevstUseContactSet extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The sevst use contact set PK. */
    @EmbeddedId
    protected SevstUseContactSetPK sevstUseContactSetPK;
    
    /** The exclus ver. */
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    
    /** The set item. */
    @Column(name = "SET_ITEM")
    private int setItem;

    /** The use mail set. */
    @Column(name = "USE_MAIL_SET")
    private int useMailSet;

	/**
	 * Instantiates a new sevst use contact set.
	 */
	public SevstUseContactSet() {
	}
    /**
     * Instantiates a new sevst use contact set.
     *
     * @param sevstUseContactSetPK the sevst use contact set PK
     */
    public SevstUseContactSet(SevstUseContactSetPK sevstUseContactSetPK) {
        this.sevstUseContactSetPK = sevstUseContactSetPK;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sevstUseContactSetPK != null ? sevstUseContactSetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SevstUseContactSet)) {
            return false;
        }
        SevstUseContactSet other = (SevstUseContactSet) object;
        if ((this.sevstUseContactSetPK == null && other.sevstUseContactSetPK != null) || (this.sevstUseContactSetPK != null && !this.sevstUseContactSetPK.equals(other.sevstUseContactSetPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "javaapplication1.SevstUseContactSet[ sevstUseContactSetPK=" + sevstUseContactSetPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.sevstUseContactSetPK;
	}
    
}
