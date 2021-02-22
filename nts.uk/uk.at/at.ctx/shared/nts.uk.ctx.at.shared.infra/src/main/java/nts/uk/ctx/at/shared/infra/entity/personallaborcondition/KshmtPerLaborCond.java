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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtPerLaborCond.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_PER_LABOR_COND")
public class KshmtPerLaborCond extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt per labor cond PK. */
    @EmbeddedId
    protected KshmtPerLaborCondPK kshmtPerLaborCondPK;
    
    /** The sche manage atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SCHE_MANAGE_ATR")
    private int scheManageAtr;
    
    /** The one day. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ONE_DAY")
    private int oneDay;
    
    /** The morning. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "MORNING")
    private int morning;
    
    /** The afternoon. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "AFTERNOON")
    private int afternoon;
    
    /** The auto stamp set atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUTO_STAMP_SET_ATR")
    private int autoStampSetAtr;

    /**
     * Instantiates a new kshmt per labor cond.
     */
    public KshmtPerLaborCond() {
    }


    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtPerLaborCondPK != null ? kshmtPerLaborCondPK.hashCode() : 0);
		return hash;
	}

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtPerLaborCond)) {
			return false;
		}
		KshmtPerLaborCond other = (KshmtPerLaborCond) object;
		if ((this.kshmtPerLaborCondPK == null && other.kshmtPerLaborCondPK != null)
				|| (this.kshmtPerLaborCondPK != null
						&& !this.kshmtPerLaborCondPK.equals(other.kshmtPerLaborCondPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtPerLaborCond[ kshmtPerLaborCondPK=" + kshmtPerLaborCondPK + " ]";
    }


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtPerLaborCondPK;
	}
    
}
