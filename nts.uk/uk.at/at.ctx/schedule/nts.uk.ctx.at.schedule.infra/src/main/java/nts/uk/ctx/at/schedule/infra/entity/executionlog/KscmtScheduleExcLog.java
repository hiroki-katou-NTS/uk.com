/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDateTime;
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
    @Column(name = "EXE_STR_D")
	@Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDateTime exeStrD;
    
    /** The exe end D. */
    @Column(name = "EXE_END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDateTime exeEndD;
    
    /** The exe sid. */
    @Column(name = "EXE_SID")
    private String exeSid;
    
    /** The start ymd. */
    @Column(name = "START_YMD")
    private int startYmd;
    
    /** The end ymd. */
    @Column(name = "END_YMD")
    private int endYmd;
    
    /** The completion status. */
    @Column(name = "COMPLETION_STATUS")
    private int completionStatus;
    
    /** The copy start ymd. */
    @Column(name = "COPY_START_YMD")
    private Integer copyStartYmd;
    
    /** The create method atr. */
    @Column(name = "CREATE_METHOD_ATR")
    private int createMethodAtr;
    
    /** The confirm. */
    @Column(name = "CONFIRM")
    private int confirm;
    
    /** The implement atr. */
    @Column(name = "IMPLEMENT_ATR")
    private int implementAtr;
    
    /** The re create atr. */
    @Column(name = "RE_CREATE_ATR")
    private int reCreateAtr;
    
    /** The process exe atr. */
    @Column(name = "PROCESS_EXE_ATR")
    private int processExeAtr;
    
    /** The re master info. */
    @Column(name = "RE_MASTER_INFO")
    private int reMasterInfo;
    
    /** The re abst hd busines. */
    @Column(name = "RE_ABST_HD_BUSINES")
    private int reAbstHdBusines;
    
    /** The re working hours. */
    @Column(name = "RE_WORKING_HOURS")
    private int reWorkingHours;
    
    /** The re time assignment. */
    @Column(name = "RE_TIME_ASSIGNMENT")
    private int reTimeAssignment;
    
    /** The re dir line bounce. */
    @Column(name = "RE_DIR_LINE_BOUNCE")
    private int reDirLineBounce;
    
    /** The re time child care. */
    @Column(name = "RE_TIME_CHILD_CARE")
    private int reTimeChildCare;

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
