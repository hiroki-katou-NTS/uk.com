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
 * The Class KshmtOtTimeZone.
 */
@Entity
@Getter
@Setter
@Table(name = "KSHMT_OT_TIME_ZONE")
public class KshmtOtTimeZone extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt ot time zone PK. */
	@EmbeddedId
	protected KshmtOtTimeZonePK kshmtOtTimeZonePK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The restrict time. */
	@Column(name = "RESTRICT_TIME")
	private int restrictTime;

	/** The ot frame no. */
	@Column(name = "OT_FRAME_NO")
	private int otFrameNo;

	/** The unit. */
	@Column(name = "UNIT")
	private int unit;

	/** The rounding. */
	@Column(name = "ROUNDING")
	private int rounding;

	/** The passage time. */
	@Column(name = "PASSAGE_TIME")
	private int passageTime;

	/** The in legal ot frame no. */
	@Column(name = "IN_LEGAL_OT_FRAME_NO")
	private Integer inLegalOtFrameNo;

	/** The settlement order. */
	@Column(name = "SETTLEMENT_ORDER")
	private Integer settlementOrder;

	/**
	 * Instantiates a new kshmt ot time zone.
	 */
	public KshmtOtTimeZone() {
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
		hash += (kshmtOtTimeZonePK != null ? kshmtOtTimeZonePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtOtTimeZone)) {
			return false;
		}
		KshmtOtTimeZone other = (KshmtOtTimeZone) object;
		if ((this.kshmtOtTimeZonePK == null && other.kshmtOtTimeZonePK != null)
				|| (this.kshmtOtTimeZonePK != null
						&& !this.kshmtOtTimeZonePK.equals(other.kshmtOtTimeZonePK))) {
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
		return this.kshmtOtTimeZonePK;
	}

}
