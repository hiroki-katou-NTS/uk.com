/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtDifHolTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_DIF_HOL_TS")
public class KshmtWtDifHolTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt diff time hol set PK. */
	@EmbeddedId
	protected KshmtWtDifHolTsPK kshmtWtDifHolTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

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

	/** The hol time. */
	@Column(name = "HOL_TIME")
	private int holTime;

	/** The hol frame no. */
	@Column(name = "HOL_FRAME_NO")
	private int holFrameNo;

	/** The out hol time. */
	@Column(name = "OUT_HOL_TIME")
	private int outHolTime;

	/** The out hol frame no. */
	@Column(name = "OUT_HOL_FRAME_NO")
	private int outHolFrameNo;

	/** The pub hol time. */
	@Column(name = "PUB_HOL_TIME")
	private int pubHolTime;

	/** The pub hol frame no. */
	@Column(name = "PUB_HOL_FRAME_NO")
	private int pubHolFrameNo;

	/**
	 * Instantiates a new kshmt diff time hol set.
	 */
	public KshmtWtDifHolTs() {
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
		hash += (kshmtWtDifHolTsPK != null ? kshmtWtDifHolTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtDifHolTs)) {
			return false;
		}
		KshmtWtDifHolTs other = (KshmtWtDifHolTs) object;
		if ((this.kshmtWtDifHolTsPK == null && other.kshmtWtDifHolTsPK != null)
				|| (this.kshmtWtDifHolTsPK != null
						&& !this.kshmtWtDifHolTsPK.equals(other.kshmtWtDifHolTsPK))) {
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
		return this.kshmtWtDifHolTsPK;
	}

}
