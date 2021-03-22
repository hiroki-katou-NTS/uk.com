/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.dailyunit;

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
 * The Class KscdtExtBudgetDailyPK.
 */
@Embeddable
@Setter
@Getter
public class KscdtExtBudgetDailyPK implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The wkpid. */
    @Basic(optional = false)
    @Column(name = "WKPID")
    private String wkpid;
    
    /** The actual date. */
    @Basic(optional = false)
    @Column(name = "YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate actualDate;
    
    /** The ext budget cd. */
    @Basic(optional = false)
    @Column(name = "EXT_BUDGET_CD")
    private String extBudgetCd;

    /**
     * Instantiates a new kscdt ext budget daily PK.
     */
    public KscdtExtBudgetDailyPK() {
    }

    /**
     * Instantiates a new kscdt ext budget daily PK.
     *
     * @param wkpid the wkpid
     * @param processD the process D
     * @param extBudgetCd the ext budget cd
     */
    public KscdtExtBudgetDailyPK(String wkpid, GeneralDate processD, String extBudgetCd) {
        this.wkpid = wkpid;
        this.actualDate = processD;
        this.extBudgetCd = extBudgetCd;
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
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscdtExtBudgetDailyPK)) {
            return false;
        }
        KscdtExtBudgetDailyPK other = (KscdtExtBudgetDailyPK) object;
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
        return true;
    }

}
