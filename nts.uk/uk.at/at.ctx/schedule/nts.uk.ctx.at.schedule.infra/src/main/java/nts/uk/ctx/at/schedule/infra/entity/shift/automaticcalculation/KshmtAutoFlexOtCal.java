/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.automaticcalculation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtAutoFlexOtCal.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_AUTO_FLEX_OT_CAL")
public class KshmtAutoFlexOtCal extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt auto flex ot cal PK. */
	@EmbeddedId
	protected KshmtAutoFlexOtCalPK kshmtAutoFlexOtCalPK;

	/** The flex ot time. */
	@Column(name = "FLEX_OT_TIME")
	private short flexOtTime;

	/** The flex ot night time. */
	@Column(name = "FLEX_OT_NIGHT_TIME")
	private short flexOtNightTime;

	/**
	 * Instantiates a new kshmt auto flex ot cal.
	 */
	public KshmtAutoFlexOtCal() {
		super();
	}

	/**
	 * Instantiates a new kshmt auto flex ot cal.
	 *
	 * @param kshmtAutoFlexOtCalPK
	 *            the kshmt auto flex ot cal PK
	 */
	public KshmtAutoFlexOtCal(KshmtAutoFlexOtCalPK kshmtAutoFlexOtCalPK) {
		this.kshmtAutoFlexOtCalPK = kshmtAutoFlexOtCalPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtAutoFlexOtCalPK != null ? kshmtAutoFlexOtCalPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtAutoFlexOtCal)) {
			return false;
		}
		KshmtAutoFlexOtCal other = (KshmtAutoFlexOtCal) object;
		if ((this.kshmtAutoFlexOtCalPK == null && other.kshmtAutoFlexOtCalPK != null)
				|| (this.kshmtAutoFlexOtCalPK != null
						&& !this.kshmtAutoFlexOtCalPK.equals(other.kshmtAutoFlexOtCalPK))) {
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
		return this.kshmtAutoFlexOtCalPK;
	}

}
