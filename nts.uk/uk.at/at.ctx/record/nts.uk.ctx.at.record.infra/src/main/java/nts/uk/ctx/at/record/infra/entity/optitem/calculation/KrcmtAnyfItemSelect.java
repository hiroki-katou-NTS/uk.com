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
 * The Class KrcmtAnyfItemSelect.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_ANYF_ITEM_SELECT")
public class KrcmtAnyfItemSelect extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcmt calc item selection PK. */
	@EmbeddedId
	protected KrcmtAnyfItemSelectPK krcmtAnyfItemSelectPK;

	/** The krcmt opt item formula. */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
		@JoinColumn(name = "OPTIONAL_ITEM_NO", referencedColumnName = "OPTIONAL_ITEM_NO", insertable = false, updatable = false),
		@JoinColumn(name = "FORMULA_ID", referencedColumnName = "FORMULA_ID", insertable = false, updatable = false)
		})
	private KrcmtAnyf krcmtAnyf;

	/** The minus segment. */
	@Column(name = "MINUS_SEGMENT")
	private int minusSegment;

	/** The operator. */
	@Column(name = "OPERATOR")
	private int operator;

	/**
	 * Instantiates a new krcmt calc item selection.
	 */
	public KrcmtAnyfItemSelect() {
		super();
	}

	/**
	 * Instantiates a new krcmt calc item selection.
	 *
	 * @param krcmtAnyfItemSelectPK the krcmt calc item selection PK
	 */
	public KrcmtAnyfItemSelect(KrcmtAnyfItemSelectPK krcmtAnyfItemSelectPK) {
		this.krcmtAnyfItemSelectPK = krcmtAnyfItemSelectPK;
	}

	public KrcmtAnyfItemSelect(KrcmtAnyfPK pk) {
		this.krcmtAnyfItemSelectPK = new KrcmtAnyfItemSelectPK();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtAnyfItemSelectPK != null ? krcmtAnyfItemSelectPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtAnyfItemSelect)) {
			return false;
		}
		KrcmtAnyfItemSelect other = (KrcmtAnyfItemSelect) object;
		if ((this.krcmtAnyfItemSelectPK == null && other.krcmtAnyfItemSelectPK != null)
				|| (this.krcmtAnyfItemSelectPK != null
						&& !this.krcmtAnyfItemSelectPK.equals(other.krcmtAnyfItemSelectPK))) {
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
		return this.krcmtAnyfItemSelectPK;
	}

}
