/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtFlexOtTimeSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLEX_OT_TIME_SET")
public class KshmtFlexOtTimeSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex ot time set PK. */
	@EmbeddedId
	protected KshmtFlexOtTimeSetPK kshmtFlexOtTimeSetPK;

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
	 * Instantiates a new kshmt flex ot time set.
	 */
	public KshmtFlexOtTimeSet() {
		super();
	}
	

	/**
	 * Instantiates a new kshmt flex ot time set.
	 *
	 * @param kshmtFlexOtTimeSetPK the kshmt flex ot time set PK
	 */
	public KshmtFlexOtTimeSet(KshmtFlexOtTimeSetPK kshmtFlexOtTimeSetPK) {
		super();
		this.kshmtFlexOtTimeSetPK = kshmtFlexOtTimeSetPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFlexOtTimeSetPK != null ? kshmtFlexOtTimeSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexOtTimeSet)) {
			return false;
		}
		KshmtFlexOtTimeSet other = (KshmtFlexOtTimeSet) object;
		if ((this.kshmtFlexOtTimeSetPK == null && other.kshmtFlexOtTimeSetPK != null)
				|| (this.kshmtFlexOtTimeSetPK != null
						&& !this.kshmtFlexOtTimeSetPK.equals(other.kshmtFlexOtTimeSetPK))) {
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
		return this.kshmtFlexOtTimeSetPK;
	}


}
