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
 * The Class KshmtWtComGoout.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_COM_GOOUT")
public class KshmtWtComGoout extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt worktime go out set PK. */
	@EmbeddedId
	protected KshmtWtComGooutPK kshmtWtComGooutPK;

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
	public KshmtWtComGoout() {
		super();
	}


	/**
	 * Instantiates a new kshmt worktime go out set.
	 *
	 * @param kshmtWtComGooutPK the kshmt worktime go out set PK
	 */
	public KshmtWtComGoout(KshmtWtComGooutPK kshmtWtComGooutPK) {
		super();
		this.kshmtWtComGooutPK = kshmtWtComGooutPK;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtComGooutPK != null ? kshmtWtComGooutPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtComGoout)) {
			return false;
		}
		KshmtWtComGoout other = (KshmtWtComGoout) object;
		if ((this.kshmtWtComGooutPK == null && other.kshmtWtComGooutPK != null)
				|| (this.kshmtWtComGooutPK != null
						&& !this.kshmtWtComGooutPK.equals(other.kshmtWtComGooutPK))) {
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
		return this.kshmtWtComGooutPK;
	}

}
