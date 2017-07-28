/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KbddtExtBudgetDaily.
 */
@Setter
@Getter
@Entity
@Table(name = "KSCDT_EXT_BUDGET_DAILY")
public class KscdtExtBudgetDaily extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The kbddt ext budget daily PK. */
    @EmbeddedId
    protected KscdtExtBudgetDailyPK kbddtExtBudgetDailyPK;

    /** The budget atr. */
    @Basic(optional = false)
    @Column(name = "BUDGET_ATR")
    private int budgetAtr;
    
    /** The actual val. */
    @Basic(optional = false)
    @Column(name = "ACTUAL_VAL")
    private Long actualVal;
    
    /** The ext budget cd. */
    @Basic(optional = false)
    @Column(name = "EXT_BUDGET_CD")
    private String extBudgetCd;

    /** The process D. */
    @Basic(optional = false)
    @Column(name = "PROCESS_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate processD;

    /**
     * Instantiates a new kbddt ext budget daily.
     */
    public KscdtExtBudgetDaily() {
    }

    /**
     * Instantiates a new kbddt ext budget daily.
     *
     * @param kbddtExtBudgetDailyPK the kbddt ext budget daily PK
     */
    public KscdtExtBudgetDaily(KscdtExtBudgetDailyPK kbddtExtBudgetDailyPK) {
        this.kbddtExtBudgetDailyPK = kbddtExtBudgetDailyPK;
    }

    /**
     * Instantiates a new kbddt ext budget daily.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     */
    public KscdtExtBudgetDaily(String cid, String wkpid) {
        this.kbddtExtBudgetDailyPK = new KscdtExtBudgetDailyPK(cid, wkpid);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kbddtExtBudgetDailyPK != null ? kbddtExtBudgetDailyPK.hashCode() : 0);
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
        if ((this.kbddtExtBudgetDailyPK == null && other.kbddtExtBudgetDailyPK != null)
                || (this.kbddtExtBudgetDailyPK != null
                        && !this.kbddtExtBudgetDailyPK.equals(other.kbddtExtBudgetDailyPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.getKbddtExtBudgetDailyPK();
    }

}
