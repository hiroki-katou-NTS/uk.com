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
 * The Class KshmtWtFleBrFlHolTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLE_BR_FL_HOL_TS")
public class KshmtWtFleBrFlHolTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex od rest set PK. */
	@EmbeddedId
	protected KshmtWtFleBrFlHolTsPK kshmtWtFleBrFlHolTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The flow rest time. */
	@Column(name = "FLOW_REST_TIME")
	private int flowRestTime;

	/** The flow passage time. */
	@Column(name = "FLOW_PASSAGE_TIME")
	private int flowPassageTime;

	/**
	 * Instantiates a new kshmt flex od rest set.
	 */
	public KshmtWtFleBrFlHolTs() {
		super();
	}

	/**
	 * Instantiates a new kshmt flex od rest set.
	 *
	 * @param kshmtWtFleBrFlHolTsPK
	 *            the kshmt flex od rest set PK
	 */
	public KshmtWtFleBrFlHolTs(KshmtWtFleBrFlHolTsPK kshmtWtFleBrFlHolTsPK) {
		super();
		this.kshmtWtFleBrFlHolTsPK = kshmtWtFleBrFlHolTsPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFleBrFlHolTsPK != null ? kshmtWtFleBrFlHolTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFleBrFlHolTs)) {
			return false;
		}
		KshmtWtFleBrFlHolTs other = (KshmtWtFleBrFlHolTs) object;
		if ((this.kshmtWtFleBrFlHolTsPK == null && other.kshmtWtFleBrFlHolTsPK != null)
				|| (this.kshmtWtFleBrFlHolTsPK != null
						&& !this.kshmtWtFleBrFlHolTsPK.equals(other.kshmtWtFleBrFlHolTsPK))) {
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
		return this.kshmtWtFleBrFlHolTsPK;
	}

}
