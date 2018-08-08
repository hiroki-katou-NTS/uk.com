/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtEmpMonthDaySet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_EMP_MONTH_DAY_SET")
public class KshmtEmpMonthDaySet extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt emp month day set PK. */
    @EmbeddedId
    protected KshmtEmpMonthDaySetPK kshmtEmpMonthDaySetPK;
   
    /** The in legal hd. */
    @Column(name = "IN_LEGAL_HD")
    private double inLegalHd;

    /**
     * Instantiates a new kshmt emp month day set.
     */
    public KshmtEmpMonthDaySet() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtEmpMonthDaySetPK != null ? kshmtEmpMonthDaySetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtEmpMonthDaySet)) {
            return false;
        }
        KshmtEmpMonthDaySet other = (KshmtEmpMonthDaySet) object;
        if ((this.kshmtEmpMonthDaySetPK == null && other.kshmtEmpMonthDaySetPK != null) || (this.kshmtEmpMonthDaySetPK != null && !this.kshmtEmpMonthDaySetPK.equals(other.kshmtEmpMonthDaySetPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtEmpMonthDaySetPK;
	}
    
}
