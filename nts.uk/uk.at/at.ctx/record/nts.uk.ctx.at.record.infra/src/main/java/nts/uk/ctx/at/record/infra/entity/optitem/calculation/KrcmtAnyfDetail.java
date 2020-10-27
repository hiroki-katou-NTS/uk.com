/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

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
 * The Class KrcmtAnyfDetail.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_ANYF_DETAIL")
public class KrcmtAnyfDetail extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcmt formula setting PK. */
	@EmbeddedId
	protected KrcmtAnyfDetailPK krcmtAnyfDetailPK;


	/** The minus segment. */
	@Column(name = "MINUS_SEGMENT")
	private int minusSegment;

	/** The operator. */
	@Column(name = "OPERATOR")
	private int operator;

	/** The left set method. */
	@Column(name = "LEFT_SET_METHOD")
	private int leftSetMethod;

	/** The left input val. */
	@Column(name = "LEFT_INPUT_VAL")
	private BigDecimal leftInputVal;

	/** The left formula item id. */
	@Column(name = "LEFT_FORMULA_ITEM_ID")
	private String leftFormulaItemId;

	/** The right set method. */
	@Column(name = "RIGHT_SET_METHOD")
	private int rightSetMethod;

	/** The right input val. */
	@Column(name = "RIGHT_INPUT_VAL")
	private BigDecimal rightInputVal;

	/** The right formula item id. */
	@Column(name = "RIGHT_FORMULA_ITEM_ID")
	private String rightFormulaItemId;

	/**
	 * Instantiates a new krcmt formula setting.
	 */
	public KrcmtAnyfDetail() {
		super();
	}

	/**
	 * Instantiates a new krcmt formula setting.
	 *
	 * @param pk the pk
	 */
	public KrcmtAnyfDetail(KrcmtAnyfPK pk) {
		super();
		this.krcmtAnyfDetailPK = new KrcmtAnyfDetailPK(pk);
	}

	/**
	 * Instantiates a new krcmt formula setting.
	 *
	 * @param krcmtAnyfDetailPK the krcmt formula setting PK
	 */
	public KrcmtAnyfDetail(KrcmtAnyfDetailPK krcmtAnyfDetailPK) {
		this.krcmtAnyfDetailPK = krcmtAnyfDetailPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtAnyfDetailPK != null ? krcmtAnyfDetailPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtAnyfDetail)) {
			return false;
		}
		KrcmtAnyfDetail other = (KrcmtAnyfDetail) object;
		if ((this.krcmtAnyfDetailPK == null && other.krcmtAnyfDetailPK != null)
				|| (this.krcmtAnyfDetailPK != null
						&& !this.krcmtAnyfDetailPK.equals(other.krcmtAnyfDetailPK))) {
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
		return this.krcmtAnyfDetailPK;
	}

}
