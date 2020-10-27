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
 * The Class KshmtWtFloStmpRefTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLO_STMP_REF_TS")
public class KshmtWtFloStmpRefTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flow stamp reflect PK. */
	@EmbeddedId
	protected KshmtWtFloStmpRefTsPK kshmtWtFloStmpRefTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The str clock. */
	@Column(name = "STR_CLOCK")
	private int strClock;

	/** The end clock. */
	@Column(name = "END_CLOCK")
	private int endClock;

	/**
	 * Instantiates a new kshmt flow stamp reflect.
	 */
	public KshmtWtFloStmpRefTs() {
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
		hash += (kshmtWtFloStmpRefTsPK != null ? kshmtWtFloStmpRefTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFloStmpRefTs)) {
			return false;
		}
		KshmtWtFloStmpRefTs other = (KshmtWtFloStmpRefTs) object;
		if ((this.kshmtWtFloStmpRefTsPK == null && other.kshmtWtFloStmpRefTsPK != null)
				|| (this.kshmtWtFloStmpRefTsPK != null
						&& !this.kshmtWtFloStmpRefTsPK.equals(other.kshmtWtFloStmpRefTsPK))) {
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
		return this.kshmtWtFloStmpRefTsPK;
	}

}
