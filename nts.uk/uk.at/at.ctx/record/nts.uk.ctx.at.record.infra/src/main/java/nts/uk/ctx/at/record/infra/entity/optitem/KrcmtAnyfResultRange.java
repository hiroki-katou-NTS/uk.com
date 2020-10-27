/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KrcmtAnyfResultRange.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_ANYF_RESULT_RANGE")
public class KrcmtAnyfResultRange extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst calc result range PK. */
	@EmbeddedId
	protected KrcmtAnyfResultRangePK krcmtAnyfResultRangePK;

	/** The upper limit atr. */
	@Column(name = "UPPER_LIMIT_ATR")
	private int upperLimitAtr;

	/** The lower limit atr. */
	@Column(name = "LOWER_LIMIT_ATR")
	private int lowerLimitAtr;

	/** The upper time range. */
	@Column(name = "UPPER_TIME_RANGE")
	private Integer upperTimeRange;

	/** The lower time range. */
	@Column(name = "LOWER_TIME_RANGE")
	private Integer lowerTimeRange;

	/** The upper number range. */
	@Column(name = "UPPER_NUMBER_RANGE")
	private Double upperNumberRange;

	/** The lower number range. */
	@Column(name = "LOWER_NUMBER_RANGE")
	private Double lowerNumberRange;

	/** The upper amount range. */
	@Column(name = "UPPER_AMOUNT_RANGE")
	private Integer upperAmountRange;

	/** The lower amount range. */
	@Column(name = "LOWER_AMOUNT_RANGE")
	private Integer lowerAmountRange;

	/**
	 * Instantiates a new krcst calc result range.
	 */
	public KrcmtAnyfResultRange() {
	}

	/**
	 * Instantiates a new krcst calc result range.
	 *
	 * @param krcmtAnyfResultRangePK
	 *            the krcst calc result range PK
	 */
	public KrcmtAnyfResultRange(KrcmtAnyfResultRangePK krcmtAnyfResultRangePK) {
		this.krcmtAnyfResultRangePK = krcmtAnyfResultRangePK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtAnyfResultRangePK != null ? krcmtAnyfResultRangePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtAnyfResultRange)) {
			return false;
		}
		KrcmtAnyfResultRange other = (KrcmtAnyfResultRange) object;
		if ((this.krcmtAnyfResultRangePK == null && other.krcmtAnyfResultRangePK != null)
				|| (this.krcmtAnyfResultRangePK != null
						&& !this.krcmtAnyfResultRangePK.equals(other.krcmtAnyfResultRangePK))) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	protected Object getKey() {
		return this.krcmtAnyfResultRangePK;
	}
}
