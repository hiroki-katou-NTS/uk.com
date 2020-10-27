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
 * The Class KshmtTempWorktimeSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_TEMP_WORKTIME_SET")
public class KshmtTempWorktimeSet extends ContractUkJpaEntity implements Serializable {

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
	private int calcMethod;

	/** The out legal break frame no. */
	@Column(name = "OUT_LEGAL_BREAK_FRAME_NO")
	private int outLegalBreakFrameNo;

	/** The out legal pub hol frame no. */
	@Column(name = "OUT_LEGAL_PUB_HOL_FRAME_NO")
	private int outLegalPubHolFrameNo;

	/** The unit. */
	@Column(name = "UNIT")
	private int unit;

	/** The rounding. */
	@Column(name = "ROUNDING")
	private int rounding;

	/** The ot frame no. */
	@Column(name = "OT_FRAME_NO")
	private int otFrameNo;

	/** The il legal ot frame no. */
	@Column(name = "IL_LEGAL_OT_FRAME_NO")
	private int ilLegalOtFrameNo;

	/** The in legal break frame no. */
	@Column(name = "IN_LEGAL_BREAK_FRAME_NO")
	private int inLegalBreakFrameNo;

	/** The settlement order. */
	@Column(name = "SETTLEMENT_ORDER")
	private int settlementOrder;

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
