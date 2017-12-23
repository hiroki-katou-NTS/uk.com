/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtMedicalTimeSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_MEDICAL_TIME_SET")
public class KshmtMedicalTimeSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt medical time set PK. */
	@EmbeddedId
	protected KshmtMedicalTimeSetPK kshmtMedicalTimeSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The app time. */
	@Column(name = "APP_TIME")
	private int appTime;

	/** The unit. */
	@Column(name = "UNIT")
	private int unit;

	/** The rounding. */
	@Column(name = "ROUNDING")
	private int rounding;

	/**
	 * Instantiates a new kshmt medical time set.
	 */
	public KshmtMedicalTimeSet() {
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
		hash += (kshmtMedicalTimeSetPK != null ? kshmtMedicalTimeSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtMedicalTimeSet)) {
			return false;
		}
		KshmtMedicalTimeSet other = (KshmtMedicalTimeSet) object;
		if ((this.kshmtMedicalTimeSetPK == null && other.kshmtMedicalTimeSetPK != null)
				|| (this.kshmtMedicalTimeSetPK != null
						&& !this.kshmtMedicalTimeSetPK.equals(other.kshmtMedicalTimeSetPK))) {
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
		return this.kshmtMedicalTimeSetPK;
	}

}
