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
 * The Class KshmtLateEarlySet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_LATE_EARLY_SET")
public class KshmtLateEarlySet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt late early set PK. */
	@EmbeddedId
	protected KshmtLateEarlySetPK kshmtLateEarlySetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The is deducte from time. */
	@Column(name = "IS_DEDUCTE_FROM_TIME")
	private int isDeducteFromTime;

	/**
	 * Instantiates a new kshmt late early set.
	 */
	public KshmtLateEarlySet() {
		super();
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtLateEarlySetPK != null ? kshmtLateEarlySetPK.hashCode() : 0);
		return hash;
	}

	/**
	 * Equals.
	 *
	 * @param object
	 *            the object
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtLateEarlySet)) {
			return false;
		}
		KshmtLateEarlySet other = (KshmtLateEarlySet) object;
		if ((this.kshmtLateEarlySetPK == null && other.kshmtLateEarlySetPK != null)
				|| (this.kshmtLateEarlySetPK != null
						&& !this.kshmtLateEarlySetPK.equals(other.kshmtLateEarlySetPK))) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	protected Object getKey() {
		return this.kshmtLateEarlySetPK;
	}

}
