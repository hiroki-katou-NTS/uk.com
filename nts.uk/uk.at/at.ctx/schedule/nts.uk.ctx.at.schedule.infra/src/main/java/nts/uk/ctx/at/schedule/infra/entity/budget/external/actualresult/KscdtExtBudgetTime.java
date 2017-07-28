/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KbtdtExtBudgetTime.
 */
@Entity
@Setter
@Getter
@Table(name = "KSCDT_EXT_BUDGET_TIME")
public class KscdtExtBudgetTime extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The kbtdt ext budget time PK. */
    @EmbeddedId
    protected KscdtExtBudgetTimePK kbtdtExtBudgetTimePK;

    /** The ext budget cd. */
    @Basic(optional = false)
    @Column(name = "EXT_BUDGET_CD")
    private String extBudgetCd;

    /** The process D. */
    @Basic(optional = false)
    @Column(name = "PROCESS_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate processD;
    
    /** The list value. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kbtdtExtBudgetTime", orphanRemoval = true)
    private List<KscdtExtBudgetTimeVal> listValue;

    /**
     * Instantiates a new kbtdt ext budget time.
     */
    public KscdtExtBudgetTime() {
    }

    /**
     * Instantiates a new kbtdt ext budget time.
     *
     * @param kbtdtExtBudgetTimePK the kbtdt ext budget time PK
     */
    public KscdtExtBudgetTime(KscdtExtBudgetTimePK kbtdtExtBudgetTimePK) {
        this.kbtdtExtBudgetTimePK = kbtdtExtBudgetTimePK;
    }

    /**
     * Instantiates a new kbtdt ext budget time.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     */
    public KscdtExtBudgetTime(String cid, String wkpid) {
        this.kbtdtExtBudgetTimePK = new KscdtExtBudgetTimePK(cid, wkpid);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kbtdtExtBudgetTimePK != null ? kbtdtExtBudgetTimePK.hashCode() : 0);
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
        if ((this.kbtdtExtBudgetTimePK == null && other.kbtdtExtBudgetTimePK != null)
                || (this.kbtdtExtBudgetTimePK != null
                        && !this.kbtdtExtBudgetTimePK.equals(other.kbtdtExtBudgetTimePK))) {
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
        return this.getKbtdtExtBudgetTimePK();
    }

}
