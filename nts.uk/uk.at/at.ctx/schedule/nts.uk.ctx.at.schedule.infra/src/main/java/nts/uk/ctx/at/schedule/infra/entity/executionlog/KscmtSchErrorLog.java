/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtSchErrorLog.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_SCH_ERROR_LOG")
public class KscmtSchErrorLog implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt sch error log PK. */
    @EmbeddedId
    protected KscmtSchErrorLogPK kscmtSchErrorLogPK;
    
    /** The err content. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ERR_CONTENT")
    private String errContent;

    /**
     * Instantiates a new kscmt sch error log.
     */
    public KscmtSchErrorLog() {
    }

    /**
     * Instantiates a new kscmt sch error log.
     *
     * @param kscmtSchErrorLogPK the kscmt sch error log PK
     */
    public KscmtSchErrorLog(KscmtSchErrorLogPK kscmtSchErrorLogPK) {
        this.kscmtSchErrorLogPK = kscmtSchErrorLogPK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtSchErrorLogPK != null ? kscmtSchErrorLogPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscmtSchErrorLog)) {
            return false;
        }
        KscmtSchErrorLog other = (KscmtSchErrorLog) object;
        if ((this.kscmtSchErrorLogPK == null && other.kscmtSchErrorLogPK != null) || (this.kscmtSchErrorLogPK != null && !this.kscmtSchErrorLogPK.equals(other.kscmtSchErrorLogPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtSchErrorLog[ kscmtSchErrorLogPK=" + kscmtSchErrorLogPK + " ]";
    }
    
    
}

