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
 * The Class KshmtWtComLatetimeMng.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_COM_LATETIME_MNG")
public class KshmtWtComLatetimeMng extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt late early set PK. */
	@EmbeddedId
	protected KshmtWtComLatetimeMngPK kshmtWtComLatetimeMngPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The is deducte from time. */
	@Column(name = "IS_DEDUCTE_FROM_TIME")
	private int isDeducteFromTime;

	/**
	 * Instantiates a new kshmt late early set.
	 */
	public KshmtWtComLatetimeMng() {
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
		hash += (kshmtWtComLatetimeMngPK != null ? kshmtWtComLatetimeMngPK.hashCode() : 0);
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
		if (!(object instanceof KshmtWtComLatetimeMng)) {
			return false;
		}
		KshmtWtComLatetimeMng other = (KshmtWtComLatetimeMng) object;
		if ((this.kshmtWtComLatetimeMngPK == null && other.kshmtWtComLatetimeMngPK != null)
				|| (this.kshmtWtComLatetimeMngPK != null
						&& !this.kshmtWtComLatetimeMngPK.equals(other.kshmtWtComLatetimeMngPK))) {
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
		return this.kshmtWtComLatetimeMngPK;
	}

}
