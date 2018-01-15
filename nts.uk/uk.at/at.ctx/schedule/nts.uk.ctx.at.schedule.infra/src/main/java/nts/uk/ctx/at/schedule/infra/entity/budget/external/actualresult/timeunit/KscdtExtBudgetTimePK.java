/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.timeunit;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class KscdtExtBudgetTimePK.
 */
@Embeddable
@Setter
@Getter
public class KscdtExtBudgetTimePK implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The wkpid. */
    @Basic(optional = false)
    @Column(name = "WKPID")
    private String wkpid;
    
    /** The process D. */
    @Basic(optional = false)
    @Column(name = "YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate actualDate;
    
    /** The ext budget cd. */
    @Basic(optional = false)
    @Column(name = "EXT_BUDGET_CD")
    private String extBudgetCd;
    
    /** The period time no. */
    @Basic(optional = false)
    @Column(name = "PERIOD_TIME_NO")
    private Integer periodTimeNo;

    /**
     * Instantiates a new kscdt ext budget time PK.
     */
    public KscdtExtBudgetTimePK() {
    }
    
    /**
     * Instantiates a new kscdt ext budget time PK.
     *
     * @param wkpid the wkpid
     * @param actualDate the actual date
     * @param extBudgetCd the ext budget cd
     */
    public KscdtExtBudgetTimePK(String wkpid, GeneralDate actualDate, String extBudgetCd) {
        this.wkpid = wkpid;
        this.actualDate = actualDate;
        this.extBudgetCd = extBudgetCd;
    }

    /**
     * Instantiates a new kscdt ext budget time PK.
     *
     * @param wkpid the wkpid
     * @param actualDate the actual date
     * @param extBudgetCd the ext budget cd
     * @param periodTimeNo the period time no
     */
    public KscdtExtBudgetTimePK(String wkpid, GeneralDate actualDate, String extBudgetCd, Integer periodTimeNo) {
        this.wkpid = wkpid;
        this.actualDate = actualDate;
        this.extBudgetCd = extBudgetCd;
        this.periodTimeNo = periodTimeNo;
    }

    /**
     * Creates the entity.
     *
     * @param other the other
     * @return the kscdt ext budget time PK
     */
    public static KscdtExtBudgetTimePK createEntity(KscdtExtBudgetTimePK other) {
        return new KscdtExtBudgetTimePK(other.getWkpid(), other.getActualDate(), other.getExtBudgetCd());
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wkpid != null ? wkpid.hashCode() : 0);
        hash += (actualDate != null ? actualDate.hashCode() : 0);
        hash += (extBudgetCd != null ? extBudgetCd.hashCode() : 0);
        hash += (periodTimeNo != null ? periodTimeNo.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscdtExtBudgetTimePK)) {
            return false;
        }
        KscdtExtBudgetTimePK other = (KscdtExtBudgetTimePK) object;
        if ((this.wkpid == null && other.wkpid != null) || (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
            return false;
        }
        if ((this.actualDate == null && other.actualDate != null)
                || (this.actualDate != null && !this.actualDate.equals(other.actualDate))) {
            return false;
        }
        if ((this.extBudgetCd == null && other.extBudgetCd != null)
                || (this.extBudgetCd != null && !this.extBudgetCd.equals(other.extBudgetCd))) {
            return false;
        }
        if ((this.periodTimeNo == null && other.periodTimeNo != null)
                || (this.periodTimeNo != null && !this.periodTimeNo.equals(other.periodTimeNo))) {
            return false;
        }
        return true;
    }

}
