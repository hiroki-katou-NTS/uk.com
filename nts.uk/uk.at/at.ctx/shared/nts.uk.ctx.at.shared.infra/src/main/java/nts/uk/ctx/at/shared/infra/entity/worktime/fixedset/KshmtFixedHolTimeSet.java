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
 * The Class KshmtFixedHolTimeSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FIXED_HOL_TIME_SET")
public class KshmtFixedHolTimeSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fixed hol time set PK. */
	@EmbeddedId
	protected KshmtFixedHolTimeSetPK kshmtFixedHolTimeSetPK;

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
	 * Instantiates a new kshmt fixed hol time set.
	 */
	public KshmtFixedHolTimeSet() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt fixed hol time set.
	 *
	 * @param cid the cid
	 * @param worktimeCd the worktime cd
	 * @param worktimeNo the worktime no
	 */
	public KshmtFixedHolTimeSet(String cid, String worktimeCd, Integer worktimeNo) {
		super();
		this.kshmtFixedHolTimeSetPK = new KshmtFixedHolTimeSetPK(cid, worktimeCd, worktimeNo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFixedHolTimeSetPK != null ? kshmtFixedHolTimeSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFixedHolTimeSet)) {
			return false;
		}
		KshmtFixedHolTimeSet other = (KshmtFixedHolTimeSet) object;
		if ((this.kshmtFixedHolTimeSetPK == null && other.kshmtFixedHolTimeSetPK != null)
				|| (this.kshmtFixedHolTimeSetPK != null
						&& !this.kshmtFixedHolTimeSetPK.equals(other.kshmtFixedHolTimeSetPK))) {
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
		return this.kshmtFixedHolTimeSetPK;
	}

}
