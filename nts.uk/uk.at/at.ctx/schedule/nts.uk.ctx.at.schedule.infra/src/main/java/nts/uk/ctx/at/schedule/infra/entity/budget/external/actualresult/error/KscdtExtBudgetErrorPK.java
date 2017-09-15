/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.error;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscdtExtBudgetErrorPK.
 */
@Embeddable
@Setter
@Getter
public class KscdtExtBudgetErrorPK implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The exe id. */
    @Basic(optional = false)
    @Column(name = "EXE_ID")
    private String exeId;
    
    /** The line no. */
    @Basic(optional = false)
    @Column(name = "LINE_NO")
    private int lineNo;
    
    /** The column no. */
    @Basic(optional = false)
    @Column(name = "COLUMN_NO")
    private int columnNo;

    /**
     * Instantiates a new kscdt ext budget error PK.
     */
    public KscdtExtBudgetErrorPK() {
    }

    /**
     * Instantiates a new kscdt ext budget error PK.
     *
     * @param exeId the exe id
     * @param lineNo the line no
     * @param columnNo the column no
     */
    public KscdtExtBudgetErrorPK(String exeId, short lineNo, short columnNo) {
        this.exeId = exeId;
        this.lineNo = lineNo;
        this.columnNo = columnNo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exeId != null ? exeId.hashCode() : 0);
        hash += (int) lineNo;
        hash += (int) columnNo;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscdtExtBudgetErrorPK)) {
            return false;
        }
        KscdtExtBudgetErrorPK other = (KscdtExtBudgetErrorPK) object;
        if ((this.exeId == null && other.exeId != null) || (this.exeId != null && !this.exeId.equals(other.exeId))) {
            return false;
        }
        if (this.lineNo != other.lineNo) {
            return false;
        }
        if (this.columnNo != other.columnNo) {
            return false;
        }
        return true;
    }

}
