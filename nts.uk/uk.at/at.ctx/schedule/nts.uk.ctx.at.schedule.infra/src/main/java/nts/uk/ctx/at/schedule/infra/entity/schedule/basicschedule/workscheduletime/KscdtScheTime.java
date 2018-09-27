/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletime;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee.KscdtScheFeeTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscdtScheTime.
 */
@Entity
@Getter
@Setter
@Table(name = "KSCDT_SCHE_TIME")
public class KscdtScheTime extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt work sch time PK. */
    @EmbeddedId
    protected KscdtScheTimePK kscdtScheTimePK;
    
    /** The break time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "BREAK_TIME")
    private int breakTime;
    
    /** The working time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WORKING_TIME")
    private int workingTime;
    
    /** The weekday time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WEEKDAY_TIME")
    private int weekdayTime;
    
    /** The prescribed time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRESCRIBED_TIME")
    private int prescribedTime;
    
    /** The total labor time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_LABOR_TIME")
    private int totalLaborTime;
    
    /** The child time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILD_TIME")
    private int childTime;

    /** The care time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CARE_TIME")
    private int careTime;
    
    /** The flex time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLEX_TIME")
    private int flexTime;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kscdtScheTime", orphanRemoval = true, fetch = FetchType.LAZY)
	public List<KscdtScheFeeTime> kscdtScheFeeTime;
    
    @OneToOne
	@JoinColumns({
			@JoinColumn(name = "SID", referencedColumnName = "KSCDT_SCHE_BASIC.SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "KSCDT_SCHE_BASIC.YMD", insertable = false, updatable = false) })
	public KscdtBasicSchedule kscdtBasicSchedule;

    /**
     * Instantiates a new kscmt work sch time.
     */
    public KscdtScheTime() {
    }

    /**
     * Instantiates a new kscmt work sch time.
     *
     * @param kscmtWorkSchTimePK the kscmt work sch time PK
     */
    public KscdtScheTime(KscdtScheTimePK kscmtWorkSchTimePK) {
        this.kscdtScheTimePK = kscmtWorkSchTimePK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscdtScheTimePK != null ? kscdtScheTimePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscdtScheTime)) {
            return false;
        }
        KscdtScheTime other = (KscdtScheTime) object;
        if ((this.kscdtScheTimePK == null && other.kscdtScheTimePK != null) || (this.kscdtScheTimePK != null && !this.kscdtScheTimePK.equals(other.kscdtScheTimePK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtWorkSchTime[ kscmtWorkSchTimePK=" + kscdtScheTimePK + " ]";
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscdtScheTimePK;
	}

	public KscdtScheTime(KscdtScheTimePK kscdtScheTimePK, int breakTime, int workingTime, int weekdayTime,
			int prescribedTime, int totalLaborTime, int childTime, int careTime, int flexTime) {
		super();
		this.kscdtScheTimePK = kscdtScheTimePK;
		this.breakTime = breakTime;
		this.workingTime = workingTime;
		this.weekdayTime = weekdayTime;
		this.prescribedTime = prescribedTime;
		this.totalLaborTime = totalLaborTime;
		this.childTime = childTime;
		this.careTime = careTime;
		this.flexTime = flexTime;
	}
	
}
