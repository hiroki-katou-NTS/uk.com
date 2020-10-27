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
 * The Class KshmtFworkHolidayTime.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FWORK_HOLIDAY_TIME")
public class KshmtFworkHolidayTime extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fwork holiday time PK. */
	@EmbeddedId
	protected KshmtFworkHolidayTimePK kshmtFworkHolidayTimePK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The use inlegal break rest time. */
	@Column(name = "USE_INLEGAL_BREAK_REST_TIME")
	private int useInlegalBreakRestTime;

	/** The inlegal break rest time. */
	@Column(name = "INLEGAL_BREAK_REST_TIME")
	private int inlegalBreakRestTime;

	/** The use out legalb reak rest time. */
	@Column(name = "USE_OUT_LEGALB_REAK_REST_TIME")
	private int useOutLegalbReakRestTime;

	/** The out legalb reak rest time. */
	@Column(name = "OUT_LEGALB_REAK_REST_TIME")
	private int outLegalbReakRestTime;

	/** The use out legal pubhol rest time. */
	@Column(name = "USE_OUT_LEGAL_PUBHOL_REST_TIME")
	private int useOutLegalPubholRestTime;

	/** The out legal pubhol rest time. */
	@Column(name = "OUT_LEGAL_PUBHOL_REST_TIME")
	private int outLegalPubholRestTime;

	/** The unit. */
	@Column(name = "UNIT")
	private int unit;

	/** The rounding. */
	@Column(name = "ROUNDING")
	private int rounding;

	/** The passage time. */
	@Column(name = "PASSAGE_TIME")
	private int passageTime;

	/**
	 * Instantiates a new kshmt fwork holiday time.
	 */
	public KshmtFworkHolidayTime() {
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
		hash += (kshmtFworkHolidayTimePK != null ? kshmtFworkHolidayTimePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFworkHolidayTime)) {
			return false;
		}
		KshmtFworkHolidayTime other = (KshmtFworkHolidayTime) object;
		if ((this.kshmtFworkHolidayTimePK == null && other.kshmtFworkHolidayTimePK != null)
				|| (this.kshmtFworkHolidayTimePK != null
						&& !this.kshmtFworkHolidayTimePK.equals(other.kshmtFworkHolidayTimePK))) {
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
		return this.kshmtFworkHolidayTimePK;
	}

}
