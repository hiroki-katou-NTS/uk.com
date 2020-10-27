/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.error;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscdtExtBudgetError.
 */
@Entity
@Setter
@Getter
@Table(name = "KSCDT_EXT_BUDGET_ERROR")
public class KscdtExtBudgetError extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The kscdt ext budget error PK. */
    @EmbeddedId
    private KscdtExtBudgetErrorPK kscdtExtBudgetErrorPK;

    /** The accepted wkpcd. */
    @Basic(optional = false)
    @Column(name = "ACCEPTED_WKPCD")
    private String acceptedWkpcd;
    
    /** The accepted D. */
    @Basic(optional = false)
    @Column(name = "ACCEPTED_D")
    private String acceptedD;
    
    /** The accepted val. */
    @Basic(optional = false)
    @Column(name = "ACCEPTED_VAL")
    private String acceptedVal;
    
    /** The err content. */
    @Basic(optional = false)
    @Column(name = "ERR_CONTENT")
    private String errContent;

    /**
     * Instantiates a new kscdt ext budget error.
     */
    public KscdtExtBudgetError() {
    }

    /**
     * Instantiates a new kscdt ext budget error.
     *
     * @param kscdtExtBudgetErrorPK the kscdt ext budget error PK
     */
    public KscdtExtBudgetError(KscdtExtBudgetErrorPK kscdtExtBudgetErrorPK) {
        this.kscdtExtBudgetErrorPK = kscdtExtBudgetErrorPK;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscdtExtBudgetErrorPK != null ? kscdtExtBudgetErrorPK.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // not set
        if (!(object instanceof KscdtExtBudgetError)) {
            return false;
        }
        KscdtExtBudgetError other = (KscdtExtBudgetError) object;
        if ((this.kscdtExtBudgetErrorPK == null && other.kscdtExtBudgetErrorPK != null)
                || (this.kscdtExtBudgetErrorPK != null
                        && !this.kscdtExtBudgetErrorPK.equals(other.kscdtExtBudgetErrorPK))) {
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
        return this.getKscdtExtBudgetErrorPK();
    }
}
