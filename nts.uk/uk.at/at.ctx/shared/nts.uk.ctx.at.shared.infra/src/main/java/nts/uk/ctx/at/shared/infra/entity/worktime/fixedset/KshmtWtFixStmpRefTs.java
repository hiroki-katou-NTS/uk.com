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
 * The Class KshmtWtFixStmpRefTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FIX_STMP_REF_TS")
public class KshmtWtFixStmpRefTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fixed stamp reflect PK. */
	@EmbeddedId
	protected KshmtWtFixStmpRefTsPK kshmtWtFixStmpRefTsPK;

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
	 * Instantiates a new kshmt fixed stamp reflect.
	 */
	public KshmtWtFixStmpRefTs() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFixStmpRefTsPK != null ? kshmtWtFixStmpRefTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFixStmpRefTs)) {
			return false;
		}
		KshmtWtFixStmpRefTs other = (KshmtWtFixStmpRefTs) object;
		if ((this.kshmtWtFixStmpRefTsPK == null && other.kshmtWtFixStmpRefTsPK != null)
				|| (this.kshmtWtFixStmpRefTsPK != null
						&& !this.kshmtWtFixStmpRefTsPK.equals(other.kshmtWtFixStmpRefTsPK))) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getKey() {
		return this.kshmtWtFixStmpRefTsPK;
	}

}
