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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtFlowRtSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLOW_RT_SET")
public class KshmtFlowRtSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flow rt set PK. */
	@EmbeddedId
	protected KshmtFlowRtSetPK kshmtFlowRtSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The fix rest time. */
	@Column(name = "FIX_REST_TIME")
	private int fixRestTime;

	/** The use rest after set. */
	@Column(name = "USE_REST_AFTER_SET")
	private int useRestAfterSet;

	/** The after rest time. */
	@Column(name = "AFTER_REST_TIME")
	private int afterRestTime;

	/** The after passage time. */
	@Column(name = "AFTER_PASSAGE_TIME")
	private int afterPassageTime;

	/**
	 * Instantiates a new kshmt flow rt set.
	 */
	public KshmtFlowRtSet() {
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
		hash += (kshmtFlowRtSetPK != null ? kshmtFlowRtSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlowRtSet)) {
			return false;
		}
		KshmtFlowRtSet other = (KshmtFlowRtSet) object;
		if ((this.kshmtFlowRtSetPK == null && other.kshmtFlowRtSetPK != null)
				|| (this.kshmtFlowRtSetPK != null
						&& !this.kshmtFlowRtSetPK.equals(other.kshmtFlowRtSetPK))) {
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
		return this.kshmtFlowRtSetPK;
	}

}
