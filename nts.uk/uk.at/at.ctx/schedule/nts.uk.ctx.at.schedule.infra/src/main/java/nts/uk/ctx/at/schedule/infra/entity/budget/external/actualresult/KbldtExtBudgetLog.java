/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KbldtExtBudgetLog.
 */
@Entity
@Setter
@Getter
@Table(name = "KBLDT_EXT_BUDGET_LOG")
public class KbldtExtBudgetLog extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The exe id. */
    @Id
    @Basic(optional = false)
    @Column(name = "EXE_ID")
    private String exeId;
    
    /** The cid. */
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;

    /** The file name. */
    @Basic(optional = false)
    @Column(name = "FILE_NAME")
    private String fileName;

    /** The ext budget cd. */
    @Basic(optional = false)
    @Column(name = "EXT_BUDGET_CD")
    private String extBudgetCd;

    /** The failure cnt. */
    @Basic(optional = false)
    @Column(name = "FAILURE_CNT")
    private int failureCnt;

    /** The completion atr. */
    @Basic(optional = false)
    @Column(name = "COMPLETION_ATR")
    private int completionAtr;

    /** The str D. */
    @Basic(optional = false)
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate strD;

    /** The end D. */
    @Basic(optional = false)
    @Column(name = "END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endD;

    /** The success cnt. */
    @Basic(optional = false)
    @Column(name = "SUCCESS_CNT")
    private int successCnt;

    /** The sid. */
    @Basic(optional = false)
    @Column(name = "SID")
    private String sid;

    /**
     * Instantiates a new kbldt ext budget log.
     */
    public KbldtExtBudgetLog() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
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
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // not set
        if (!(object instanceof KbldtExtBudgetLog)) {
            return false;
        }
        KbldtExtBudgetLog other = (KbldtExtBudgetLog) object;
        if ((this.exeId == null && other.exeId != null)
                || (this.exeId != null && !this.exeId.equals(other.exeId))) {
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
        return this.exeId;
    }

}
