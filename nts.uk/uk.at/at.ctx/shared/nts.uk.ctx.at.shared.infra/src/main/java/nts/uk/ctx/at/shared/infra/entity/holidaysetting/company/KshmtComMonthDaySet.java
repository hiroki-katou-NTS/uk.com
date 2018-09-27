/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.holidaysetting.company;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The Class KshmtComMonthDaySet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_COM_MONTH_DAY_SET")
public class KshmtComMonthDaySet extends UkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt com month day set PK. */
    @EmbeddedId
    protected KshmtComMonthDaySetPK kshmtComMonthDaySetPK;

    /** The in legal hd. */
    @Column(name = "IN_LEGAL_HD")
    private double inLegalHd;

    /**
     * Instantiates a new kshmt com month day set.
     */
    public KshmtComMonthDaySet() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtComMonthDaySetPK != null ? kshmtComMonthDaySetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtComMonthDaySet)) {
            return false;
        }
        KshmtComMonthDaySet other = (KshmtComMonthDaySet) object;
        if ((this.kshmtComMonthDaySetPK == null && other.kshmtComMonthDaySetPK != null) || (this.kshmtComMonthDaySetPK != null && !this.kshmtComMonthDaySetPK.equals(other.kshmtComMonthDaySetPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtComMonthDaySetPK;
	}
    
}
