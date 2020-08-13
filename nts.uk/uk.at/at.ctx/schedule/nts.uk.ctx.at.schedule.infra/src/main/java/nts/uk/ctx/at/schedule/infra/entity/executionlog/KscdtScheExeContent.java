/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscdtScheExeContent.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCDT_SCHE_EXE_CONTENT")
public class KscdtScheExeContent extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The exe id. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_ID")
    private String exeId;
    
    /** The confirm. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONFIRM")
    private Integer confirm;
    
    /** The implement atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMPLEMENT_ATR")
    private Integer implementAtr;
    
    /** The copy start ymd. */
    @Column(name = "COPY_START_YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate copyStartYmd;
    
    /** The create method atr. */
    @Column(name = "CREATE_METHOD_ATR")
    private Integer createMethodAtr;
    
    /** The re create atr. */
    @Column(name = "RE_CREATE_ATR")
    private Integer reCreateAtr;
    
    /** The process exe atr. */
    @Column(name = "PROCESS_EXE_ATR")
    private Integer processExeAtr;
    
    /** The re target atr. */
    @Column(name = "RE_TARGET_ATR")
    private Integer reTargetAtr;
    
    /** The re converter. */
    @Column(name = "RE_CONVERTER")
    private Integer reConverter;
    
    /** The re emp off work. */
    @Column(name = "RE_EMP_OFF_WORK")
    private Integer reEmpOffWork;
    
    /** The re direct bouncer. */
    @Column(name = "RE_DIRECT_BOUNCER")
    private Integer reDirectBouncer;
    
    /** The re short term emp. */
    @Column(name = "RE_SHORT_TERM_EMP")
    private Integer reShortTermEmp;
    
    /** The re work type change. */
    @Column(name = "RE_WORK_TYPE_CHANGE")
    private Integer reWorkTypeChange;
    
    /** The re protect hand correct. */
    @Column(name = "RE_PROTECT_HAND_CORRECT")
    private Integer reProtectHandCorrect;
    
    /** The re working hours. */
    @Column(name = "RE_WORKING_HOURS")
    private Integer reWorkingHours;
    
    /** The re start-end time. */
    @Column(name = "RE_START_END_TIME")
    private Integer reStartEndTime;
    
    /** The re master info. */
    @Column(name = "RE_MASTER_INFO")
    private Integer reMasterInfo;
    
    /** The re time assignment. */
    @Column(name = "RE_TIME_ASSIGNMENT")
    private Integer reTimeAssignment;

    /**
     * Instantiates a new kscmt sch create content.
     */
    public KscdtScheExeContent() {
    }

    /**
     * Instantiates a new kscmt sch create content.
     *
     * @param exeId the exe id
     */
    public KscdtScheExeContent(String exeId) {
        this.exeId = exeId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exeId != null ? exeId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscdtScheExeContent)) {
            return false;
        }
        KscdtScheExeContent other = (KscdtScheExeContent) object;
        if ((this.exeId == null && other.exeId != null) || (this.exeId != null && !this.exeId.equals(other.exeId))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtSchCreateContent[ exeId=" + exeId + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.exeId;
	}
    
    
}

