/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flowset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtFloWorkTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLO_WORK_TS")
public class KshmtWtFloWorkTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flow time zone PK. */
	@EmbeddedId
	protected KshmtWtFloWorkTsPK kshmtWtFloWorkTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The unit. */
	@Column(name = "UNIT")
	private int unit;

	/** The rounding. */
	@Column(name = "ROUNDING")
	private int rounding;

	/**
	 * Instantiates a new kshmt flow time zone.
	 */
	public KshmtWtFloWorkTs() {
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
		hash += (kshmtWtFloWorkTsPK != null ? kshmtWtFloWorkTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFloWorkTs)) {
			return false;
		}
		KshmtWtFloWorkTs other = (KshmtWtFloWorkTs) object;
		if ((this.kshmtWtFloWorkTsPK == null && other.kshmtWtFloWorkTsPK != null)
				|| (this.kshmtWtFloWorkTsPK != null
						&& !this.kshmtWtFloWorkTsPK.equals(other.kshmtWtFloWorkTsPK))) {
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
		return this.kshmtWtFloWorkTsPK;
	}

}
