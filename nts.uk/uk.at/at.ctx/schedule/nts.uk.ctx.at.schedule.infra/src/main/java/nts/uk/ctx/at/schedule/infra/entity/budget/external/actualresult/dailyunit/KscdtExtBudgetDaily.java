/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.dailyunit;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscdtExtBudgetDaily.
 */
@Entity
@Setter
@Getter
@Table(name = "KSCDT_EXT_BUDGET_DAILY_OLD")
public class KscdtExtBudgetDaily extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The kscdt ext budget daily PK. */
    @EmbeddedId
    protected KscdtExtBudgetDailyPK kscdtExtBudgetDailyPK;

    /** The actual val. */
    @Basic(optional = false)
    @Column(name = "ACTUAL_VAL")
    private Integer actualVal;
    
    /**
     * Instantiates a new kscdt ext budget daily.
     */
    public KscdtExtBudgetDaily() {
    }

    /**
     * Instantiates a new kscdt ext budget daily.
     *
     * @param kbddtExtBudgetDailyPK the kbddt ext budget daily PK
     */
    public KscdtExtBudgetDaily(KscdtExtBudgetDailyPK kbddtExtBudgetDailyPK) {
        this.kscdtExtBudgetDailyPK = kbddtExtBudgetDailyPK;
    }

    /**
     * Instantiates a new kscdt ext budget daily.
     *
     * @param wkpid the wkpid
     * @param processD the process D
     * @param extBudgetCd the ext budget cd
     */
    public KscdtExtBudgetDaily(String wkpid, GeneralDate processD, String extBudgetCd) {
        this.kscdtExtBudgetDailyPK = new KscdtExtBudgetDailyPK(wkpid, processD, extBudgetCd);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscdtExtBudgetDailyPK != null ? kscdtExtBudgetDailyPK.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscdtExtBudgetDaily)) {
            return false;
        }
        KscdtExtBudgetDaily other = (KscdtExtBudgetDaily) object;
        if ((this.kscdtExtBudgetDailyPK == null && other.kscdtExtBudgetDailyPK != null)
                || (this.kscdtExtBudgetDailyPK != null
                        && !this.kscdtExtBudgetDailyPK.equals(other.kscdtExtBudgetDailyPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.getKscdtExtBudgetDailyPK();
    }

}
