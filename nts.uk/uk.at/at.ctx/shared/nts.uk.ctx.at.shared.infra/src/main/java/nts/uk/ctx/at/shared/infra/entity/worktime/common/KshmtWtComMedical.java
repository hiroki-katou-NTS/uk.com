/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtComMedical.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_COM_MEDICAL")
public class KshmtWtComMedical extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt medical time set PK. */
	@EmbeddedId
	protected KshmtWtComMedicalPK kshmtWtComMedicalPK;

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
	public KshmtWtComMedical() {
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
		hash += (kshmtWtComMedicalPK != null ? kshmtWtComMedicalPK.hashCode() : 0);
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
		if (!(object instanceof KshmtWtComMedical)) {
			return false;
		}
		KshmtWtComMedical other = (KshmtWtComMedical) object;
		if ((this.kshmtWtComMedicalPK == null && other.kshmtWtComMedicalPK != null)
				|| (this.kshmtWtComMedicalPK != null
						&& !this.kshmtWtComMedicalPK.equals(other.kshmtWtComMedicalPK))) {
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
		return this.kshmtWtComMedicalPK;
	}

}
