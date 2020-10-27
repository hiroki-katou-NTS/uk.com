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
 * The Class KshmtWorktimeGoOutSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WORKTIME_GO_OUT_SET")
public class KshmtWorktimeGoOutSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt worktime go out set PK. */
	@EmbeddedId
	protected KshmtWorktimeGoOutSetPK kshmtWorktimeGoOutSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The rounding same frame. */
	@Column(name = "ROUNDING_SAME_FRAME")
	private int roundingSameFrame;

	/** The rounding cross frame. */
	@Column(name = "ROUNDING_CROSS_FRAME")
	private int roundingCrossFrame;

	/**
	 * Instantiates a new kshmt worktime go out set.
	 */
	public KshmtWorktimeGoOutSet() {
		super();
	}


	/**
	 * Instantiates a new kshmt worktime go out set.
	 *
	 * @param kshmtWorktimeGoOutSetPK the kshmt worktime go out set PK
	 */
	public KshmtWorktimeGoOutSet(KshmtWorktimeGoOutSetPK kshmtWorktimeGoOutSetPK) {
		super();
		this.kshmtWorktimeGoOutSetPK = kshmtWorktimeGoOutSetPK;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWorktimeGoOutSetPK != null ? kshmtWorktimeGoOutSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorktimeGoOutSet)) {
			return false;
		}
		KshmtWorktimeGoOutSet other = (KshmtWorktimeGoOutSet) object;
		if ((this.kshmtWorktimeGoOutSetPK == null && other.kshmtWorktimeGoOutSetPK != null)
				|| (this.kshmtWorktimeGoOutSetPK != null
						&& !this.kshmtWorktimeGoOutSetPK.equals(other.kshmtWorktimeGoOutSetPK))) {
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
		return this.kshmtWorktimeGoOutSetPK;
	}

}
