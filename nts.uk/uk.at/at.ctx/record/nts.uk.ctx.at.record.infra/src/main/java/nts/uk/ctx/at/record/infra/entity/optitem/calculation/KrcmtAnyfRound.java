/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KrcmtAnyfRound.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_ANYF_ROUND")
public class KrcmtAnyfRound extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcmt formula rounding PK. */
	@EmbeddedId
	protected KrcmtAnyfRoundPK krcmtAnyfRoundPK;

	/** The krcmt opt item formula. */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
		@JoinColumn(name = "OPTIONAL_ITEM_NO", referencedColumnName = "OPTIONAL_ITEM_NO", insertable = false, updatable = false),
		@JoinColumn(name = "FORMULA_ID", referencedColumnName = "FORMULA_ID", insertable = false, updatable = false)
		})
	private KrcmtAnyf krcmtAnyf;

	/** The number rounding. */
	@Column(name = "NUMBER_ROUNDING")
	private int numberRounding;

	/** The time rounding. */
	@Column(name = "TIME_ROUNDING")
	private int timeRounding;

	/** The amount rounding. */
	@Column(name = "AMOUNT_ROUNDING")
	private int amountRounding;

	/** The number rounding unit. */
	@Column(name = "NUMBER_ROUNDING_UNIT")
	private int numberRoundingUnit;

	/** The time rounding unit. */
	@Column(name = "TIME_ROUNDING_UNIT")
	private int timeRoundingUnit;

	/** The amount rounding unit. */
	@Column(name = "AMOUNT_ROUNDING_UNIT")
	private int amountRoundingUnit;

	/**
	 * Instantiates a new krcmt formula rounding.
	 */
	public KrcmtAnyfRound() {
		super();
	}

	/**
	 * Instantiates a new krcmt formula rounding.
	 *
	 * @param pk the pk
	 * @param roundingAtr the rounding atr
	 */
	public KrcmtAnyfRound(KrcmtAnyfPK pk , int roundingAtr) {
		this.krcmtAnyfRoundPK = new KrcmtAnyfRoundPK(pk, roundingAtr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtAnyfRoundPK != null ? krcmtAnyfRoundPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtAnyfRound)) {
			return false;
		}
		KrcmtAnyfRound other = (KrcmtAnyfRound) object;
		if ((this.krcmtAnyfRoundPK == null && other.krcmtAnyfRoundPK != null)
				|| (this.krcmtAnyfRoundPK != null
						&& !this.krcmtAnyfRoundPK.equals(other.krcmtAnyfRoundPK))) {
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
		return this.krcmtAnyfRoundPK;
	}

}
