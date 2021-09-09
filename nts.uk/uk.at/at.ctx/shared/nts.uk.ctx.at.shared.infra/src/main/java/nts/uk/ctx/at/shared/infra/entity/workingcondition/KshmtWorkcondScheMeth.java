/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCal;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleMasterReferenceAtr;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * The Class KshmtWorkcondScheMeth.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WORKCOND_SCHE_METH")
public class KshmtWorkcondScheMeth extends ContractCompanyUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The sid. */
	@Column(name = "SID")
	private String sid;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The history id. */
	@Id
	@Column(name = "HIST_ID")
	private String historyId;

	/** The basic create method. */
	@Column(name = "BASIC_CREATE_METHOD")
	private int basicCreateMethod;

	/** The ref business day calendar. */
	@Column(name = "REF_BUSINESS_DAY_CALENDAR")
	private Integer refBusinessDayCalendar;

	/** The ref working hours. */
	@Column(name = "REF_WORKING_HOURS")
	private Integer refWorkingHours;

	/**
	 * Instantiates a new kshmt schedule method.
	 */
	public KshmtWorkcondScheMeth() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (historyId != null ? historyId.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkcondScheMeth)) {
			return false;
		}
		KshmtWorkcondScheMeth other = (KshmtWorkcondScheMeth) object;
		if ((this.historyId == null && other.historyId != null)
				|| (this.historyId != null && !this.historyId.equals(other.historyId))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.historyId;
	}

	public KshmtWorkcondScheMeth(String sid, String historyId, int basicCreateMethod, Integer refBusinessDayCalendar, Integer refWorkingHours) {
		super();
		this.sid = sid;
		this.historyId = historyId;
		this.basicCreateMethod = basicCreateMethod;
		this.refBusinessDayCalendar = refBusinessDayCalendar;
		this.refWorkingHours = refWorkingHours;
	}
	
	public ScheduleMethod toDomain() {
		WorkScheduleBusCal workScheduleBusCal = null;
		if(this.refBusinessDayCalendar != null  &&  this.refWorkingHours != null) {
			workScheduleBusCal = new WorkScheduleBusCal(
					this.refBusinessDayCalendar ==null?null:WorkScheduleMasterReferenceAtr.valueOf(this.refBusinessDayCalendar),
					this.refWorkingHours ==null?null:TimeZoneScheduledMasterAtr.valueOf(this.refWorkingHours));
		}
		return new ScheduleMethod(
				this.basicCreateMethod,
				workScheduleBusCal,
				null);
		
	}
	
	public static KshmtWorkcondScheMeth toEntity(ScheduleMethod scheduleMethod,String sid,String historyId) {
		return new KshmtWorkcondScheMeth(sid, historyId, scheduleMethod.getBasicCreateMethod().value, 
				scheduleMethod.getWorkScheduleBusCal().isPresent()?(scheduleMethod.getWorkScheduleBusCal().get().getReferenceBusinessDayCalendar()==null?null:scheduleMethod.getWorkScheduleBusCal().get().getReferenceBusinessDayCalendar().value):null,
				scheduleMethod.getWorkScheduleBusCal().isPresent()?(scheduleMethod.getWorkScheduleBusCal().get().getReferenceWorkingHours()==null?null:scheduleMethod.getWorkScheduleBusCal().get().getReferenceWorkingHours().value):null);
	}
}
