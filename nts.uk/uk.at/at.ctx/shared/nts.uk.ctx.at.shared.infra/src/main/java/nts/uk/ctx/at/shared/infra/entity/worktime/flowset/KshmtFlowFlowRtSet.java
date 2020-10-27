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
 * The Class KshmtFlowFlowRtSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLOW_FLOW_RT_SET")
public class KshmtFlowFlowRtSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flow flow rt set PK. */
	@EmbeddedId
	protected KshmtFlowFlowRtSetPK kshmtFlowFlowRtSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The rest time. */
	@Column(name = "REST_TIME")
	private int restTime;

	/** The passage time. */
	@Column(name = "PASSAGE_TIME")
	private int passageTime;

	/**
	 * Instantiates a new kshmt flow flow rt set.
	 */
	public KshmtFlowFlowRtSet() {
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
		hash += (kshmtFlowFlowRtSetPK != null ? kshmtFlowFlowRtSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlowFlowRtSet)) {
			return false;
		}
		KshmtFlowFlowRtSet other = (KshmtFlowFlowRtSet) object;
		if ((this.kshmtFlowFlowRtSetPK == null && other.kshmtFlowFlowRtSetPK != null)
				|| (this.kshmtFlowFlowRtSetPK != null
						&& !this.kshmtFlowFlowRtSetPK.equals(other.kshmtFlowFlowRtSetPK))) {
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
		return this.kshmtFlowFlowRtSetPK;
	}

}
