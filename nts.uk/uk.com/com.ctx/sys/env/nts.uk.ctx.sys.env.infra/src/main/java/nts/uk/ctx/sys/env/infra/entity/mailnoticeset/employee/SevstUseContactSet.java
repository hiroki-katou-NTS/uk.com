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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class SevstUseContactSet.
 */
@Entity
@Table(name = "SEVST_USE_CONTACT_SET")
@Getter
@Setter
public class SevstUseContactSet extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The sevst use contact set PK. */
    @EmbeddedId
    protected SevstUseContactSetPK sevstUseContactSetPK;
    
    /** The exclus ver. */
    @Column(name = "EXCLUS_VER")
    private int exclusVer;

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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + exclusVer;
		result = prime * result + ((sevstUseContactSetPK == null) ? 0 : sevstUseContactSetPK.hashCode());
		result = prime * result + useMailSet;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SevstUseContactSet other = (SevstUseContactSet) obj;
		if (exclusVer != other.exclusVer)
			return false;
		if (sevstUseContactSetPK == null) {
			if (other.sevstUseContactSetPK != null)
				return false;
		} else if (!sevstUseContactSetPK.equals(other.sevstUseContactSetPK))
			return false;
		if (useMailSet != other.useMailSet)
			return false;
		return true;
	}
    
}
