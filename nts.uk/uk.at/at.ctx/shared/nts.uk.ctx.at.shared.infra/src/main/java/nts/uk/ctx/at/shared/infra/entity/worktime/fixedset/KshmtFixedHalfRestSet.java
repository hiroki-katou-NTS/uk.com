/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.fixedset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtFixedHalfRestSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FIXED_HALF_REST_SET")
public class KshmtFixedHalfRestSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fixed half rest set PK. */
	@EmbeddedId
	protected KshmtFixedHalfRestSetPK kshmtFixedHalfRestSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The start time. */
	@Column(name = "START_TIME")
	private int startTime;

	/** The end time. */
	@Column(name = "END_TIME")
	private int endTime;

	/**
	 * Instantiates a new kshmt fixed half rest set.
	 */
	public KshmtFixedHalfRestSet() {
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
		hash += (kshmtFixedHalfRestSetPK != null ? kshmtFixedHalfRestSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFixedHalfRestSet)) {
			return false;
		}
		KshmtFixedHalfRestSet other = (KshmtFixedHalfRestSet) object;
		if ((this.kshmtFixedHalfRestSetPK == null && other.kshmtFixedHalfRestSetPK != null)
				|| (this.kshmtFixedHalfRestSetPK != null
						&& !this.kshmtFixedHalfRestSetPK.equals(other.kshmtFixedHalfRestSetPK))) {
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
		return this.kshmtFixedHalfRestSetPK;
	}

}
