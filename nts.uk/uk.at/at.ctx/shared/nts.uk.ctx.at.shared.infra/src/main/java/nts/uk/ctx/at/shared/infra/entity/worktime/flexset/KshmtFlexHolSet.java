/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtFlexHolSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLEX_HOL_SET")
public class KshmtFlexHolSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex hol set PK. */
	@EmbeddedId
	protected KshmtFlexHolSetPK kshmtFlexHolSetPK;

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
	private BigDecimal holFrameNo;

	/** The out hol time. */
	@Column(name = "OUT_HOL_TIME")
	private int outHolTime;

	/** The out hol frame no. */
	@Column(name = "OUT_HOL_FRAME_NO")
	private BigDecimal outHolFrameNo;

	/** The pub hol time. */
	@Column(name = "PUB_HOL_TIME")
	private int pubHolTime;

	/** The pub hol frame no. */
	@Column(name = "PUB_HOL_FRAME_NO")
	private BigDecimal pubHolFrameNo;

	/**
	 * Instantiates a new kshmt flex hol set.
	 */
	public KshmtFlexHolSet() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt flex hol set.
	 *
	 * @param kshmtFlexHolSetPK the kshmt flex hol set PK
	 */
	public KshmtFlexHolSet(KshmtFlexHolSetPK kshmtFlexHolSetPK) {
		super();
		this.kshmtFlexHolSetPK = kshmtFlexHolSetPK;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFlexHolSetPK != null ? kshmtFlexHolSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexHolSet)) {
			return false;
		}
		KshmtFlexHolSet other = (KshmtFlexHolSet) object;
		if ((this.kshmtFlexHolSetPK == null && other.kshmtFlexHolSetPK != null)
				|| (this.kshmtFlexHolSetPK != null
						&& !this.kshmtFlexHolSetPK.equals(other.kshmtFlexHolSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtFlexHolSetPK;
	}
	

}
