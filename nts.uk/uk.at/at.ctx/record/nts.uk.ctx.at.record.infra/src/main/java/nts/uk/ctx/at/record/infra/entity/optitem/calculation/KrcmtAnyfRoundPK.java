/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcmtAnyfRoundPK.
 */
@Getter
@Setter
@Embeddable
public class KrcmtAnyfRoundPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant DAILY_ATR. */
	public static final int DAILY_ATR = 1;

	/** The Constant MONTHLY_ATR. */
	public static final int MONTHLY_ATR = 2;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The optional item no. */
	@Column(name = "OPTIONAL_ITEM_NO")
	private Integer optionalItemNo;

	/** The formula id. */
	@Column(name = "FORMULA_ID")
	private String formulaId;

	/** The rounding atr. */
	@Column(name = "ROUNDING_ATR")
	private int roundingAtr;

	/**
	 * Instantiates a new krcmt formula rounding PK.
	 */
	public KrcmtAnyfRoundPK() {
		super();
	}

	/**
	 * Instantiates a new krcmt formula rounding PK.
	 *
	 * @param pk the pk
	 * @param roundingAtr the rounding atr
	 */
	public KrcmtAnyfRoundPK(KrcmtAnyfPK pk, int roundingAtr) {
		super();
		this.cid = pk.getCid();
		this.formulaId = pk.getFormulaId();
		this.optionalItemNo = pk.getOptionalItemNo();
		this.roundingAtr = roundingAtr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((formulaId == null) ? 0 : formulaId.hashCode());
		result = prime * result + ((optionalItemNo == null) ? 0 : optionalItemNo.hashCode());
		result = prime * result + roundingAtr;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KrcmtAnyfRoundPK other = (KrcmtAnyfRoundPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (formulaId == null) {
			if (other.formulaId != null)
				return false;
		} else if (!formulaId.equals(other.formulaId))
			return false;
		if (optionalItemNo == null) {
			if (other.optionalItemNo != null)
				return false;
		} else if (!optionalItemNo.equals(other.optionalItemNo))
			return false;
		if (roundingAtr != other.roundingAtr)
			return false;
		return true;
	}

}
