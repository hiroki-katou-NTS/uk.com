/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
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
 * The Class KshmtWtFleBrFl.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLE_BR_FL")
public class KshmtWtFleBrFl extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex rest set PK. */
	@EmbeddedId
	protected KshmtWtFleBrFlPK kshmtWtFleBrFlPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The is refer rest time. */
	@Column(name = "IS_REFER_REST_TIME")
	private int isReferRestTime;

	/** The is calc from schedule. */
	@Column(name = "IS_CALC_FROM_SCHEDULE")
	private int isCalcFromSchedule;

	/** The user private go out rest. */
	@Column(name = "USER_PRIVATE_GO_OUT_REST")
	private int userPrivateGoOutRest;

	/** The user asso go out rest. */
	@Column(name = "USER_ASSO_GO_OUT_REST")
	private int userAssoGoOutRest;

	/** The fixed rest calc method. */
	@Column(name = "FIXED_REST_CALC_METHOD")
	private int fixedRestCalcMethod;

	/** The use stamp. */
	@Column(name = "USE_STAMP")
	private int useStamp;

	/** The use stamp calc method. */
	@Column(name = "USE_STAMP_CALC_METHOD")
	private int useStampCalcMethod;

	/** The time manager set atr. */
	@Column(name = "TIME_MANAGER_SET_ATR")
	private int timeManagerSetAtr;

	/** The calculate method. */
	@Column(name = "CALCULATE_METHOD")
	private int calculateMethod;

	/** The use plural work rest time. */
	@Column(name = "USE_PLURAL_WORK_REST_TIME")
	private int usePluralWorkRestTime;

	/** The common calculate method. */
	@Column(name = "COMMON_CALCULATE_METHOD")
	private int commonCalculateMethod;
	
	/** The rest set unit. */
	@Column(name = "REST_SET_UNIT")
	private int restSetUnit;

	/** The rest set rounding. */
	@Column(name = "REST_SET_ROUNDING")
	private int restSetRounding;

	/**
	 * Instantiates a new kshmt flex rest set.
	 */
	public KshmtWtFleBrFl() {
		super();
	}

	/**
	 * Instantiates a new kshmt flex rest set.
	 *
	 * @param kshmtWtFleBrFlPK
	 *            the kshmt flex rest set PK
	 */
	public KshmtWtFleBrFl(KshmtWtFleBrFlPK kshmtWtFleBrFlPK) {
		super();
		this.kshmtWtFleBrFlPK = kshmtWtFleBrFlPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFleBrFlPK != null ? kshmtWtFleBrFlPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFleBrFl)) {
			return false;
		}
		KshmtWtFleBrFl other = (KshmtWtFleBrFl) object;
		if ((this.kshmtWtFleBrFlPK == null && other.kshmtWtFleBrFlPK != null)
				|| (this.kshmtWtFleBrFlPK != null && !this.kshmtWtFleBrFlPK.equals(other.kshmtWtFleBrFlPK))) {
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
		return this.kshmtWtFleBrFlPK;
	}

}
