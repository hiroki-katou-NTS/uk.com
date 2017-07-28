/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KbedtExtBudgetError.
 */
@Entity
@Setter
@Getter
@Table(name = "KSCDT_EXT_BUDGET_ERROR")
public class KscdtExtBudgetError extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The exe id. */
    @Id
    @Basic(optional = false)
    @Column(name = "EXE_ID")
    private String exeId;

    /** The err content. */
    @Basic(optional = false)
    @Column(name = "ERR_CONTENT")
    private String errContent;

    /** The column no. */
    @Basic(optional = false)
    @Column(name = "COLUMN_NO")
    private int columnNo;

    /** The accepted val. */
    @Basic(optional = false)
    @Column(name = "ACCEPTED_VAL")
    private String acceptedVal;

    /** The accepted D. */
    @Basic(optional = false)
    @Column(name = "ACCEPTED_D")
    private String acceptedD;

    /** The accepted wkpcd. */
    @Basic(optional = false)
    @Column(name = "ACCEPTED_WKPCD")
    private String acceptedWkpcd;

    /** The line no. */
    @Basic(optional = false)
    @Column(name = "LINE_NO")
    private int lineNo;

    /**
     * Instantiates a new kbedt ext budget error.
     */
    public KscdtExtBudgetError() {
    }

    /**
     * Instantiates a new kbedt ext budget error.
     *
     * @param exeId
     *            the exe id
     */
    public KscdtExtBudgetError(String exeId) {
        this.exeId = exeId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exeId != null ? exeId.hashCode() : 0);
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
        if ((this.exeId == null && other.exeId != null) || (this.exeId != null && !this.exeId.equals(other.exeId))) {
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
        return this.getExeId();
    }
}
