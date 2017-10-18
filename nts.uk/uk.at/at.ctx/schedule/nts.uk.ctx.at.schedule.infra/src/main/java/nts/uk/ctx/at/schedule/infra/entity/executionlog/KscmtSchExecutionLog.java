/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateTimeToDBConverter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * The Class KscmtSchExecutionLog.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_SCH_EXECUTION_LOG")
public class KscmtSchExecutionLog implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt sch execution log PK. */
    @EmbeddedId
    protected KscmtSchExecutionLogPK kscmtSchExecutionLogPK;
    
    /** The exe sid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_SID")
    private String exeSid;
    
    /** The exe str D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_STR_D")
    @Convert(converter = GeneralDateTimeToDBConverter.class)
    private GeneralDateTime exeStrD;
    
    /** The exe end D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_END_D")
    @Convert(converter = GeneralDateTimeToDBConverter.class)
    private GeneralDateTime exeEndD;
    
    /** The start ymd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate startYmd;
    
    /** The end ymd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endYmd;
    
    /** The completion status. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPLETION_STATUS")
    private int completionStatus;

    /**
     * Instantiates a new kscmt sch execution log.
     */
    public KscmtSchExecutionLog() {
    }

    /**
     * Instantiates a new kscmt sch execution log.
     *
     * @param kscmtSchExecutionLogPK the kscmt sch execution log PK
     */
    public KscmtSchExecutionLog(KscmtSchExecutionLogPK kscmtSchExecutionLogPK) {
        this.kscmtSchExecutionLogPK = kscmtSchExecutionLogPK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtSchExecutionLogPK != null ? kscmtSchExecutionLogPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscmtSchExecutionLog)) {
            return false;
        }
        KscmtSchExecutionLog other = (KscmtSchExecutionLog) object;
        if ((this.kscmtSchExecutionLogPK == null && other.kscmtSchExecutionLogPK != null) || (this.kscmtSchExecutionLogPK != null && !this.kscmtSchExecutionLogPK.equals(other.kscmtSchExecutionLogPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtSchExecutionLog[ kscmtSchExecutionLogPK=" + kscmtSchExecutionLogPK + " ]";
    }
    
}

