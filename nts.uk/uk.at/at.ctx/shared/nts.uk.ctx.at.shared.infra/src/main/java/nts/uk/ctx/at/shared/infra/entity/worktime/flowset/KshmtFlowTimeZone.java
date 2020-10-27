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
 * The Class KshmtFlowTimeZone.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLOW_TIME_ZONE")
public class KshmtFlowTimeZone extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flow time zone PK. */
	@EmbeddedId
	protected KshmtFlowTimeZonePK kshmtFlowTimeZonePK;

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
	public KshmtFlowTimeZone() {
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
		hash += (kshmtFlowTimeZonePK != null ? kshmtFlowTimeZonePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlowTimeZone)) {
			return false;
		}
		KshmtFlowTimeZone other = (KshmtFlowTimeZone) object;
		if ((this.kshmtFlowTimeZonePK == null && other.kshmtFlowTimeZonePK != null)
				|| (this.kshmtFlowTimeZonePK != null
						&& !this.kshmtFlowTimeZonePK.equals(other.kshmtFlowTimeZonePK))) {
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
		return this.kshmtFlowTimeZonePK;
	}

}
