/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.log;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateTimeToDBConverter;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscdtExtBudgetLog.
 */
@Entity
@Setter
@Getter
@Table(name = "KSCDT_EXT_BUDGET_LOG")
public class KscdtExtBudgetLog extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The exe id. */
    @Id
    @Basic(optional = false)
    @Column(name = "EXE_ID")
    private String exeId;
    
    /** The sid. */
    @Basic(optional = false)
    @Column(name = "SID")
    private String sid;
    
    /** The str date time. */
    @Basic(optional = false)
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateTimeToDBConverter.class)
    private GeneralDateTime strDateTime;

    /** The end date time. */
    @Basic(optional = false)
    @Column(name = "END_D")
    @Convert(converter = GeneralDateTimeToDBConverter.class)
    private GeneralDateTime endDateTime;
    
    /** The ext budget cd. */
    @Basic(optional = false)
    @Column(name = "EXT_BUDGET_CD")
    private String extBudgetCd;
    
    /** The file name. */
    @Basic(optional = false)
    @Column(name = "FILE_NAME")
    private String fileName;
    
    /** The completion atr. */
    @Basic(optional = false)
    @Column(name = "COMPLETION_ATR")
    private int completionAtr;

    /** The success cnt. */
    @Basic(optional = false)
    @Column(name = "SUCCESS_CNT")
    private int successCnt;
    
    /** The failure cnt. */
    @Basic(optional = false)
    @Column(name = "FAILURE_CNT")
    private int failureCnt;

    /**
     * Instantiates a new kscdt ext budget log.
     */
    public KscdtExtBudgetLog() {
    }
    
    /**
     * Instantiates a new kscdt ext budget log.
     *
     * @param exeId the exe id
     */
    public KscdtExtBudgetLog(String exeId) {
        this.exeId = exeId;
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
        if (!(object instanceof KscdtExtBudgetLog)) {
            return false;
        }
        KscdtExtBudgetLog other = (KscdtExtBudgetLog) object;
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
