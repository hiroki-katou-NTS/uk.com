/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtFlowWorkSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLOW_WORK_SET")
public class KshmtFlowWorkSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flow work set PK. */
	@EmbeddedId
	protected KshmtFlowWorkSetPK kshmtFlowWorkSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The fixed change atr. */
	@Column(name = "FIXED_CHANGE_ATR")
	private int fixedChangeAtr;

	/** The calc str time set. */
	@Column(name = "CALC_STR_TIME_SET")
	private int calcStrTimeSet;

	/** The legal ot set. */
	@Column(name = "LEGAL_OT_SET")
	private int legalOtSet;

	/**
	 * Instantiates a new kshmt flow work set.
	 */
	public KshmtFlowWorkSet() {
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
		hash += (kshmtFlowWorkSetPK != null ? kshmtFlowWorkSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlowWorkSet)) {
			return false;
		}
		KshmtFlowWorkSet other = (KshmtFlowWorkSet) object;
		if ((this.kshmtFlowWorkSetPK == null && other.kshmtFlowWorkSetPK != null)
				|| (this.kshmtFlowWorkSetPK != null
						&& !this.kshmtFlowWorkSetPK.equals(other.kshmtFlowWorkSetPK))) {
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
		return this.kshmtFlowWorkSetPK;
	}

}
