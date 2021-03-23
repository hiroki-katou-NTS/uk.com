/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule;

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
 * The Class KscdtScheChildCare.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCDT_SCHE_CHILD_CARE")
public class KscdtScheChildCare extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscdt sche child care PK. */
    @EmbeddedId
    protected KscdtScheChildCarePK kscdtScheChildCarePK;
    
    /** The str time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_TIME")
    private int strTime;
    
    /** The end time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_TIME")
    private int endTime;
 
    
    /** The child care atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILD_CARE_ATR")
    private int childCareAtr;

    /**
     * Instantiates a new kscmt child care sch.
     */
    public KscdtScheChildCare() {
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscdtScheChildCarePK != null ? kscdtScheChildCarePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		if (!(object instanceof KscdtScheChildCare)) {
			return false;
		}
		KscdtScheChildCare other = (KscdtScheChildCare) object;
		if ((this.kscdtScheChildCarePK == null && other.kscdtScheChildCarePK != null)
				|| (this.kscdtScheChildCarePK != null
						&& !this.kscdtScheChildCarePK.equals(other.kscdtScheChildCarePK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtChildCareSch[ kscdtScheChildCarePK=" + kscdtScheChildCarePK + " ]";
    }


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getKscdtScheChildCarePK();
	}
    
    
}
