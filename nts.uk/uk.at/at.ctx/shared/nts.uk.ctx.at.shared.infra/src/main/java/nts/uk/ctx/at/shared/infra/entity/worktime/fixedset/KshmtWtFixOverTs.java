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
 * The Class KshmtWtFixOverTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FIX_OVER_TS")
public class KshmtWtFixOverTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fixed ot time set PK. */
	@EmbeddedId
	protected KshmtWtFixOverTsPK kshmtWtFixOverTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The treat time work. */
	@Column(name = "TREAT_TIME_WORK")
	private int treatTimeWork;

	/** The treat early ot work. */
	@Column(name = "TREAT_EARLY_OT_WORK")
	private int treatEarlyOtWork;

	/** The unit. */
	@Column(name = "UNIT")
	private int unit;

	/** The rounding. */
	@Column(name = "ROUNDING")
	private int rounding;

	/** The time str. */
	@Column(name = "TIME_STR")
	private int timeStr;

	/** The time end. */
	@Column(name = "TIME_END")
	private int timeEnd;

	// TODO: Move into PK
	/** The ot frame no. */
	@Column(name = "OT_FRAME_NO")
	private int otFrameNo;

	/** The legal ot frame no. */
	@Column(name = "LEGAL_OT_FRAME_NO")
	private Integer legalOtFrameNo;

	/** The payoff order. */
	@Column(name = "PAYOFF_ORDER")
	private Integer payoffOrder;

	/**
	 * Instantiates a new kshmt fixed ot time set.
	 */
	public KshmtWtFixOverTs() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt fixed ot time set.
	 *
	 * @param kshmtWtFixOverTsPK the kshmt fixed ot time set PK
	 */
	public KshmtWtFixOverTs(KshmtWtFixOverTsPK kshmtWtFixOverTsPK) {
		super();
		this.kshmtWtFixOverTsPK = kshmtWtFixOverTsPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFixOverTsPK != null ? kshmtWtFixOverTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFixOverTs)) {
			return false;
		}
		KshmtWtFixOverTs other = (KshmtWtFixOverTs) object;
		if ((this.kshmtWtFixOverTsPK == null && other.kshmtWtFixOverTsPK != null)
				|| (this.kshmtWtFixOverTsPK != null
						&& !this.kshmtWtFixOverTsPK.equals(other.kshmtWtFixOverTsPK))) {
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
		return this.kshmtWtFixOverTsPK;
	}

}
