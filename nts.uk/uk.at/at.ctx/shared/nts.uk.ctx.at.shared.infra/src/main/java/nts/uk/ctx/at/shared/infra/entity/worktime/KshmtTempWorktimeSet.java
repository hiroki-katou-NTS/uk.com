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
 * The Class KshmtTempWorktimeSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_TEMP_WORKTIME_SET")
public class KshmtTempWorktimeSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt temp worktime set PK. */
	@EmbeddedId
	protected KshmtTempWorktimeSetPK kshmtTempWorktimeSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The calc method. */
	@Column(name = "CALC_METHOD")
	private short calcMethod;

	/** The out legal break frame no. */
	@Column(name = "OUT_LEGAL_BREAK_FRAME_NO")
	private short outLegalBreakFrameNo;

	/** The out legal pub hol frame no. */
	@Column(name = "OUT_LEGAL_PUB_HOL_FRAME_NO")
	private short outLegalPubHolFrameNo;

	/** The unit. */
	@Column(name = "UNIT")
	private short unit;

	/** The rounding. */
	@Column(name = "ROUNDING")
	private short rounding;

	/** The ot frame no. */
	@Column(name = "OT_FRAME_NO")
	private short otFrameNo;

	/** The il legal ot frame no. */
	@Column(name = "IL_LEGAL_OT_FRAME_NO")
	private short ilLegalOtFrameNo;

	/** The in legal break frame no. */
	@Column(name = "IN_LEGAL_BREAK_FRAME_NO")
	private short inLegalBreakFrameNo;

	/** The settlement order. */
	@Column(name = "SETTLEMENT_ORDER")
	private short settlementOrder;

	/**
	 * Instantiates a new kshmt temp worktime set.
	 */
	public KshmtTempWorktimeSet() {
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
		hash += (kshmtTempWorktimeSetPK != null ? kshmtTempWorktimeSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtTempWorktimeSet)) {
			return false;
		}
		KshmtTempWorktimeSet other = (KshmtTempWorktimeSet) object;
		if ((this.kshmtTempWorktimeSetPK == null && other.kshmtTempWorktimeSetPK != null)
				|| (this.kshmtTempWorktimeSetPK != null
						&& !this.kshmtTempWorktimeSetPK.equals(other.kshmtTempWorktimeSetPK))) {
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
		return this.kshmtTempWorktimeSetPK;
	}

}
