/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;

/**
 * The Class KshmtWorkCatTimeZone.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_WORK_CAT_TIME_ZONE")
public class KshmtWorkCatTimeZone extends KshmtTimeZone implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt work cat time zone PK. */
	@EmbeddedId
	protected KshmtWorkCatTimeZonePK kshmtWorkCatTimeZonePK;

	/**
	 * Instantiates a new kshmt work cat time zone.
	 */
	public KshmtWorkCatTimeZone() {
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
		hash += (kshmtWorkCatTimeZonePK != null ? kshmtWorkCatTimeZonePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkCatTimeZone)) {
			return false;
		}
		KshmtWorkCatTimeZone other = (KshmtWorkCatTimeZone) object;
		if ((this.kshmtWorkCatTimeZonePK == null && other.kshmtWorkCatTimeZonePK != null)
				|| (this.kshmtWorkCatTimeZonePK != null
						&& !this.kshmtWorkCatTimeZonePK.equals(other.kshmtWorkCatTimeZonePK))) {
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
		return this.kshmtWorkCatTimeZonePK;
	}

	public KshmtWorkCatTimeZone(KshmtWorkCatTimeZonePK kshmtWorkCatTimeZonePK) {
		super();
		this.kshmtWorkCatTimeZonePK = kshmtWorkCatTimeZonePK;
	}
	
	public TimeZone toDomain() {
		return new TimeZone(NotUseAtr.valueOf(this.getUseAtr()), this.kshmtWorkCatTimeZonePK.getCnt(), this.getStartTime(), this.getEndTime());
		
	}
	
	public static KshmtWorkCatTimeZone toEntity(TimeZone timeZone,String historyId,int workCategoryAtr) {
		KshmtWorkCatTimeZone data =  new KshmtWorkCatTimeZone(new KshmtWorkCatTimeZonePK(historyId, workCategoryAtr, timeZone.getCnt()));
		data.setUseAtr(timeZone.getUseAtr().value);
		data.setStartTime(timeZone.getStart().valueAsMinutes());
		data.setEndTime(timeZone.getEnd().valueAsMinutes());
		return data;
	}
	
}
