/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.timeunit;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscdtExtBudgetTime.
 */
@Entity
@Setter
@Getter
@Table(name = "KSCDT_EXT_BUDGET_TIME")
public class KscdtExtBudgetTime extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The kscdt ext budget time PK. */
    @EmbeddedId
    protected KscdtExtBudgetTimePK kscdtExtBudgetTimePK;
    
    /** The actual val. */
    @Basic(optional = false)
    @Column(name = "ACTUAL_VAL")
    private Integer actualVal;

    /**
     * Instantiates a new kscdt ext budget time.
     */
    public KscdtExtBudgetTime() {
    }

    /**
     * Instantiates a new kscdt ext budget time.
     *
     * @param kscdtExtBudgetTimePK the kscdt ext budget time PK
     */
    public KscdtExtBudgetTime(KscdtExtBudgetTimePK kscdtExtBudgetTimePK) {
        this.kscdtExtBudgetTimePK = kscdtExtBudgetTimePK;
    }

    /**
     * Instantiates a new kscdt ext budget time.
     *
     * @param wkpid the wkpid
     * @param actualDate the actual date
     * @param extBudgetCd the ext budget cd
     * @param periodTimeNo the period time no
     */
    public KscdtExtBudgetTime(String wkpid, GeneralDate actualDate, String extBudgetCd, Integer periodTimeNo) {
        this.kscdtExtBudgetTimePK = new KscdtExtBudgetTimePK(wkpid, actualDate, extBudgetCd, periodTimeNo);
    }
    
    /**
     * Instantiates a new kscdt ext budget time.
     *
     * @param wkpid the wkpid
     * @param actualDate the actual date
     * @param extBudgetCd the ext budget cd
     */
    public KscdtExtBudgetTime(String wkpid, GeneralDate actualDate, String extBudgetCd) {
        this.kscdtExtBudgetTimePK = new KscdtExtBudgetTimePK(wkpid, actualDate, extBudgetCd);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscdtExtBudgetTimePK != null ? kscdtExtBudgetTimePK.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscdtExtBudgetTime)) {
            return false;
        }
        KscdtExtBudgetTime other = (KscdtExtBudgetTime) object;
        if ((this.kscdtExtBudgetTimePK == null && other.kscdtExtBudgetTimePK != null)
                || (this.kscdtExtBudgetTimePK != null
                        && !this.kscdtExtBudgetTimePK.equals(other.kscdtExtBudgetTimePK))) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.getKscdtExtBudgetTimePK();
    }

}
