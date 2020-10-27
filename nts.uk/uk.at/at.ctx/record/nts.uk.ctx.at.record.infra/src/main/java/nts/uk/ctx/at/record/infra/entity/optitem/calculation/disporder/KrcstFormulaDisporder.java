/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.calculation.disporder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KrcstFormulaDisporder.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_FORMULA_DISPORDER")
public class KrcstFormulaDisporder extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst formula disporder PK. */
	@EmbeddedId
	protected KrcstFormulaDisporderPK krcstFormulaDisporderPK;

	/** The disporder. */
	@Column(name = "DISPORDER")
	private int disporder;

	/**
	 * Instantiates a new krcst formula disporder.
	 */
	public KrcstFormulaDisporder() {
		super();
	}

	/**
	 * Instantiates a new krcst formula disporder.
	 *
	 * @param krcstFormulaDisporderPK
	 *            the krcst formula disporder PK
	 */
	public KrcstFormulaDisporder(KrcstFormulaDisporderPK krcstFormulaDisporderPK) {
		this.krcstFormulaDisporderPK = krcstFormulaDisporderPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcstFormulaDisporderPK != null ? krcstFormulaDisporderPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcstFormulaDisporder)) {
			return false;
		}
		KrcstFormulaDisporder other = (KrcstFormulaDisporder) object;
		if ((this.krcstFormulaDisporderPK == null && other.krcstFormulaDisporderPK != null)
				|| (this.krcstFormulaDisporderPK != null
						&& !this.krcstFormulaDisporderPK.equals(other.krcstFormulaDisporderPK))) {
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
		return this.krcstFormulaDisporderPK;
	}

}
