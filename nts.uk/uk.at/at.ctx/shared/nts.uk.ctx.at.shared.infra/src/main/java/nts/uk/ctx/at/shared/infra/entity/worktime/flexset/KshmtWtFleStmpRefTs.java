/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtFleStmpRefTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLE_STMP_REF_TS")
public class KshmtWtFleStmpRefTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex stamp reflect PK. */
	@EmbeddedId
	protected KshmtWtFleStmpRefTsPK kshmtWtFleStmpRefTsPK;

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
	 * Instantiates a new kshmt flex stamp reflect.
	 */
	public KshmtWtFleStmpRefTs() {
		super();
	}

	/**
	 * Instantiates a new kshmt flex stamp reflect.
	 *
	 * @param kshmtWtFleStmpRefTsPK the kshmt flex stamp reflect PK
	 */
	public KshmtWtFleStmpRefTs(KshmtWtFleStmpRefTsPK kshmtWtFleStmpRefTsPK) {
		super();
		this.kshmtWtFleStmpRefTsPK = kshmtWtFleStmpRefTsPK;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFleStmpRefTsPK != null ? kshmtWtFleStmpRefTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFleStmpRefTs)) {
			return false;
		}
		KshmtWtFleStmpRefTs other = (KshmtWtFleStmpRefTs) object;
		if ((this.kshmtWtFleStmpRefTsPK == null && other.kshmtWtFleStmpRefTsPK != null)
				|| (this.kshmtWtFleStmpRefTsPK != null
						&& !this.kshmtWtFleStmpRefTsPK.equals(other.kshmtWtFleStmpRefTsPK))) {
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
		return this.kshmtWtFleStmpRefTsPK;
	}

}
