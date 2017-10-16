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
import nts.arc.layer.infra.data.entity.type.GeneralDateTimeToDBConverter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
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
    
    /** The exe sid. */
    @Column(name = "EXE_SID")
    private String exeSid;
    
    /** The exe str D. */
    @Column(name = "EXE_STR_D")
    @Convert(converter = GeneralDateTimeToDBConverter.class)
    private GeneralDateTime exeStrD;

    /** The exe end D. */
    @Column(name = "EXE_END_D")
    @Convert(converter = GeneralDateTimeToDBConverter.class)
    private GeneralDateTime exeEndD;

    /** The start ymd. */
    @Column(name = "START_YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate startYmd;

    /** The end ymd. */
    @Column(name = "END_YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endYmd;

    /** The completion status. */
    @Column(name = "COMPLETION_STATUS")
    private Integer completionStatus;
    
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

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtScheduleExcLogPK != null ? kscmtScheduleExcLogPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscmtScheduleExcLog)) {
            return false;
        }
        KscmtScheduleExcLog other = (KscmtScheduleExcLog) object;
        if ((this.kscmtScheduleExcLogPK == null && other.kscmtScheduleExcLogPK != null) || (this.kscmtScheduleExcLogPK != null && !this.kscmtScheduleExcLogPK.equals(other.kscmtScheduleExcLogPK))) {
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

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getKscmtScheduleExcLogPK();
	}
    
}
