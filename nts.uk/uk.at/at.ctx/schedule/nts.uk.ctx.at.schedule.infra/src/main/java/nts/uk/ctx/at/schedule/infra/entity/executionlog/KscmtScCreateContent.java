/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import java.io.Serializable;

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
 * The Class KscmtScCreateContent.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_SC_CREATE_CONTENT")
public class KscmtScCreateContent extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The exe id. */
    @Id
    @NotNull
    @Column(name = "EXE_ID")
    private String exeId;
    
    /** The confirm. */
    @NotNull
    @Column(name = "CONFIRM")
    private int confirm;
    
    /** The implement atr. */
    @NotNull
    @Column(name = "IMPLEMENT_ATR")
    private int implementAtr;

    /** The copy start ymd. */
    @Column(name = "COPY_START_YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate copyStartYmd;
    
    /** The create method atr. */
    @Column(name = "CREATE_METHOD_ATR")
    private int createMethodAtr;
    
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
     * Instantiates a new kscmt sc create content.
     */
    public KscmtScCreateContent() {
    }

    /**
     * Instantiates a new kscmt sc create content.
     *
     * @param exeId the exe id
     */
    public KscmtScCreateContent(String exeId) {
        this.exeId = exeId;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exeId != null ? exeId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscmtScCreateContent)) {
            return false;
        }
        KscmtScCreateContent other = (KscmtScCreateContent) object;
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
        return "entity.KscmtScCreateContent[ exeId=" + exeId + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.exeId;
	}
}
