/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.employeeinfo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscmtPewScheduleCreset.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_PEW_SCHEDULE_CRESET")
public class KscmtPewScheduleCreset extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The sid. */
    @Id
    @Column(name = "SID")
    private String sid;
    
    /** The basic cre method. */
    @Column(name = "BASIC_CRE_METHOD")
    private int basicCreMethod;
    
    /** The mp workschedule cre. */
    @Column(name = "MP_WORKSCHEDULE_CRE")
    private Integer mpWorkscheduleCre;
    
    /** The refer bus day cal. */
    @Column(name = "REFER_BUS_DAY_CAL")
    private Integer referBusDayCal;
    
    /** The refer basic work. */
    @Column(name = "REFER_BASIC_WORK")
    private Integer referBasicWork;
    
    /** The refer working hour. */
    @Column(name = "REFER_WORKING_HOUR")
    private Integer referWorkingHour;

    /**
     * Instantiates a new kscmt pew schedule creset.
     */
    public KscmtPewScheduleCreset() {
    }

    /**
     * Instantiates a new kscmt pew schedule creset.
     *
     * @param sid the sid
     */
    public KscmtPewScheduleCreset(String sid) {
        this.sid = sid;
    }


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscmtPewScheduleCreset)) {
			return false;
		}
		KscmtPewScheduleCreset other = (KscmtPewScheduleCreset) object;
		if ((this.sid == null && other.sid != null)
				|| (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KscmtPewScheduleCreset[ sid=" + sid + " ]";
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.sid;
	}
	
    
}
