/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KbtdtExtBudgetTimeVal.
 */
@Entity
@Setter
@Getter
@Table(name = "KSCDT_EXT_BUDGET_TIME_VAL")
public class KscdtExtBudgetTimeVal extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The kbtdt ext budget time val PK. */
    @EmbeddedId
    protected KscdtExtBudgetTimeValPK kbtdtExtBudgetTimeValPK;

    /** The period time no. */
    @Basic(optional = false)
    @Column(name = "PERIOD_TIME_NO")
    private int periodTimeNo;

    /** The budget atr. */
    @Basic(optional = false)
    @Column(name = "BUDGET_ATR")
    private int budgetAtr;
    
    /** The actual val. */
    @Basic(optional = false)
    @Column(name = "ACTUAL_VAL")
    private Long actualVal;
    
    /** The kbtdt ext budget time. */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "CID", referencedColumnName = "KBTDT_EXT_BUDGET_TIME.CID", insertable = false,
                updatable = false),
            @JoinColumn(name = "WKPID", referencedColumnName = "KBTDT_EXT_BUDGET_TIME.WKPID", insertable = false,
                updatable = false) })
    private KscdtExtBudgetTime kbtdtExtBudgetTime;

    /**
     * Instantiates a new kbtdt ext budget time val.
     */
    public KscdtExtBudgetTimeVal() {
    }

    /**
     * Instantiates a new kbtdt ext budget time val.
     *
     * @param kbtdtExtBudgetTimeValPK
     *            the kbtdt ext budget time val PK
     */
    public KscdtExtBudgetTimeVal(KscdtExtBudgetTimeValPK kbtdtExtBudgetTimeValPK) {
        this.kbtdtExtBudgetTimeValPK = kbtdtExtBudgetTimeValPK;
    }

    /**
     * Instantiates a new kbtdt ext budget time val.
     *
     * @param cid
     *            the cid
     * @param wkpid
     *            the wkpid
     */
    public KscdtExtBudgetTimeVal(String cid, String wkpid) {
        this.kbtdtExtBudgetTimeValPK = new KscdtExtBudgetTimeValPK(cid, wkpid);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kbtdtExtBudgetTimeValPK != null ? kbtdtExtBudgetTimeValPK.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscdtExtBudgetTimeVal)) {
            return false;
        }
        KscdtExtBudgetTimeVal other = (KscdtExtBudgetTimeVal) object;
        if ((this.kbtdtExtBudgetTimeValPK == null && other.kbtdtExtBudgetTimeValPK != null)
                || (this.kbtdtExtBudgetTimeValPK != null
                        && !this.kbtdtExtBudgetTimeValPK.equals(other.kbtdtExtBudgetTimeValPK))) {
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
        return this.getKbtdtExtBudgetTimeValPK();
    }
}
