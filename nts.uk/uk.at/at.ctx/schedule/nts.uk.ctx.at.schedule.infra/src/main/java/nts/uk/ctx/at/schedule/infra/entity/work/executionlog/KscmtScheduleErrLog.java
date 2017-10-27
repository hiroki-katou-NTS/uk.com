/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.work.executionlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscmtScheduleErrLog.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_SCHEDULE_ERR_LOG")
public class KscmtScheduleErrLog extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt schedule err log PK. */
    @EmbeddedId
    protected KscmtScheduleErrLogPK kscmtScheduleErrLogPK;
    
    /** The err content. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ERR_CONTENT")
    private String errContent;
    
    /** The ymd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "YMD")
    private int ymd;

    /**
     * Instantiates a new kscmt schedule err log.
     */
    public KscmtScheduleErrLog() {
    }

    /**
     * Instantiates a new kscmt schedule err log.
     *
     * @param kscmtScheduleErrLogPK the kscmt schedule err log PK
     */
    public KscmtScheduleErrLog(KscmtScheduleErrLogPK kscmtScheduleErrLogPK) {
        this.kscmtScheduleErrLogPK = kscmtScheduleErrLogPK;
    }


    /**
     * Instantiates a new kscmt schedule err log.
     *
     * @param exeId the exe id
     * @param sid the sid
     */
    public KscmtScheduleErrLog(String exeId, String sid) {
        this.kscmtScheduleErrLogPK = new KscmtScheduleErrLogPK(exeId, sid);
    }

    /**
     * Gets the kscmt schedule err log PK.
     *
     * @return the kscmt schedule err log PK
     */
    public KscmtScheduleErrLogPK getKscmtScheduleErrLogPK() {
        return kscmtScheduleErrLogPK;
    }

    /**
     * Sets the kscmt schedule err log PK.
     *
     * @param kscmtScheduleErrLogPK the new kscmt schedule err log PK
     */
    public void setKscmtScheduleErrLogPK(KscmtScheduleErrLogPK kscmtScheduleErrLogPK) {
        this.kscmtScheduleErrLogPK = kscmtScheduleErrLogPK;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtScheduleErrLogPK != null ? kscmtScheduleErrLogPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		if (!(object instanceof KscmtScheduleErrLog)) {
			return false;
		}
		KscmtScheduleErrLog other = (KscmtScheduleErrLog) object;
		if ((this.kscmtScheduleErrLogPK == null && other.kscmtScheduleErrLogPK != null)
				|| (this.kscmtScheduleErrLogPK != null
						&& !this.kscmtScheduleErrLogPK.equals(other.kscmtScheduleErrLogPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtScheduleErrLog[ kscmtScheduleErrLogPK=" + kscmtScheduleErrLogPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtScheduleErrLogPK;
	}
    
}
