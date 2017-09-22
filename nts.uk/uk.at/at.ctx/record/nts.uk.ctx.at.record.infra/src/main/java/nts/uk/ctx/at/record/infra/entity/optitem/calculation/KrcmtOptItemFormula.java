/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KrcmtOptItemFormula.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_OPT_ITEM_FORMULA")
public class KrcmtOptItemFormula extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcmt opt item formula PK. */
	@EmbeddedId
	protected KrcmtOptItemFormulaPK krcmtOptItemFormulaPK;

	/** The formula name. */
	@Column(name = "FORMULA_NAME")
	private String formulaName;

	/** The formula atr. */
	@Column(name = "FORMULA_ATR")
	private int formulaAtr;

	/** The symbol. */
	@Column(name = "SYMBOL")
	private String symbol;

	/** The krcmt formula roundings. */
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<KrcmtFormulaRounding> krcmtFormulaRoundings;

	/**
	 * Instantiates a new krcmt opt item formula.
	 */
	public KrcmtOptItemFormula() {
		super();
	}

	/**
	 * Instantiates a new krcmt opt item formula.
	 *
	 * @param krcmtOptItemFormulaPK
	 *            the krcmt opt item formula PK
	 */
	public KrcmtOptItemFormula(KrcmtOptItemFormulaPK krcmtOptItemFormulaPK) {
		this.krcmtOptItemFormulaPK = krcmtOptItemFormulaPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtOptItemFormulaPK != null ? krcmtOptItemFormulaPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtOptItemFormula)) {
			return false;
		}
		KrcmtOptItemFormula other = (KrcmtOptItemFormula) object;
		if ((this.krcmtOptItemFormulaPK == null && other.krcmtOptItemFormulaPK != null)
				|| (this.krcmtOptItemFormulaPK != null
						&& !this.krcmtOptItemFormulaPK.equals(other.krcmtOptItemFormulaPK))) {
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
		return this.krcmtOptItemFormulaPK;
	}

}
