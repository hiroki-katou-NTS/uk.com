/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.work.executionlog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscmtScheduleExcLog.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_SCHEDULE_EXC_LOG")
public class KscmtScheduleExcLog extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt schedule exc log PK. */
    @EmbeddedId
    protected KscmtScheduleExcLogPK kscmtScheduleExcLogPK;
    
    /** The exe str D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_STR_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exeStrD;
    
    /** The exe end D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_END_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exeEndD;
    
    /** The exe sid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_SID")
    private String exeSid;
    
    /** The start ymd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_YMD")
    private int startYmd;
    
    /** The end ymd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_YMD")
    private int endYmd;
    
    /** The completion status. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPLETION_STATUS")
    private short completionStatus;
    
    /** The copy start ymd. */
    @Column(name = "COPY_START_YMD")
    private Integer copyStartYmd;
    
    /** The create method atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATE_METHOD_ATR")
    private short createMethodAtr;
    
    /** The confirm. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONFIRM")
    private short confirm;
    
    /** The implement atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMPLEMENT_ATR")
    private short implementAtr;
    
    /** The re create atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "RE_CREATE_ATR")
    private short reCreateAtr;
    
    /** The process exe atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROCESS_EXE_ATR")
    private short processExeAtr;
    
    /** The re master info. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "RE_MASTER_INFO")
    private short reMasterInfo;
    
    /** The re abst hd busines. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "RE_ABST_HD_BUSINES")
    private short reAbstHdBusines;
    
    /** The re working hours. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "RE_WORKING_HOURS")
    private short reWorkingHours;
    
    /** The re time assignment. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "RE_TIME_ASSIGNMENT")
    private short reTimeAssignment;
    
    /** The re dir line bounce. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "RE_DIR_LINE_BOUNCE")
    private short reDirLineBounce;
    
    /** The re time child care. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "RE_TIME_CHILD_CARE")
    private short reTimeChildCare;

    /**
     * Instantiates a new kscmt schedule exc log.
     */
    public KscmtScheduleExcLog() {
    }

    /**
     * Instantiates a new kscmt schedule exc log.
     *
     * @param kscmtScheduleExcLogPK the kscmt schedule exc log PK
     */
    public KscmtScheduleExcLog(KscmtScheduleExcLogPK kscmtScheduleExcLogPK) {
        this.kscmtScheduleExcLogPK = kscmtScheduleExcLogPK;
    }


    /**
     * Instantiates a new kscmt schedule exc log.
     *
     * @param cid the cid
     * @param exeId the exe id
     */
    public KscmtScheduleExcLog(String cid, String exeId) {
        this.kscmtScheduleExcLogPK = new KscmtScheduleExcLogPK(cid, exeId);
    }

    /**
     * Gets the kscmt schedule exc log PK.
     *
     * @return the kscmt schedule exc log PK
     */
    public KscmtScheduleExcLogPK getKscmtScheduleExcLogPK() {
        return kscmtScheduleExcLogPK;
    }

    /**
     * Sets the kscmt schedule exc log PK.
     *
     * @param kscmtScheduleExcLogPK the new kscmt schedule exc log PK
     */
    public void setKscmtScheduleExcLogPK(KscmtScheduleExcLogPK kscmtScheduleExcLogPK) {
        this.kscmtScheduleExcLogPK = kscmtScheduleExcLogPK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtScheduleExcLogPK != null ? kscmtScheduleExcLogPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KscmtScheduleExcLog)) {
			return false;
		}
		KscmtScheduleExcLog other = (KscmtScheduleExcLog) object;
		if ((this.kscmtScheduleExcLogPK == null && other.kscmtScheduleExcLogPK != null)
				|| (this.kscmtScheduleExcLogPK != null
						&& !this.kscmtScheduleExcLogPK.equals(other.kscmtScheduleExcLogPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtScheduleExcLog[ kscmtScheduleExcLogPK=" + kscmtScheduleExcLogPK + " ]";
    }

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	protected Object getKey() {
		return this.kscmtScheduleExcLogPK;
	}
    
    
}
