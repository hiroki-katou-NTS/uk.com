/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtEmployeeMonthDaySet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_EMPLOYEE_MONTH_DAY_SET")
public class KshmtEmployeeMonthDaySet extends UkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt employee month day set PK. */
    @EmbeddedId
    protected KshmtEmployeeMonthDaySetPK kshmtEmployeeMonthDaySetPK;

    /** The in legal hd. */
    @Column(name = "IN_LEGAL_HD")
    private double inLegalHd;

    /**
     * Instantiates a new kshmt employee month day set.
     */
    public KshmtEmployeeMonthDaySet() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtEmployeeMonthDaySetPK != null ? kshmtEmployeeMonthDaySetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtEmployeeMonthDaySet)) {
            return false;
        }
        KshmtEmployeeMonthDaySet other = (KshmtEmployeeMonthDaySet) object;
        if ((this.kshmtEmployeeMonthDaySetPK == null && other.kshmtEmployeeMonthDaySetPK != null) || (this.kshmtEmployeeMonthDaySetPK != null && !this.kshmtEmployeeMonthDaySetPK.equals(other.kshmtEmployeeMonthDaySetPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entities.KshmtEmployeeMonthDaySet[ kshmtEmployeeMonthDaySetPK=" + kshmtEmployeeMonthDaySetPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtEmployeeMonthDaySetPK;
	}
    
}
