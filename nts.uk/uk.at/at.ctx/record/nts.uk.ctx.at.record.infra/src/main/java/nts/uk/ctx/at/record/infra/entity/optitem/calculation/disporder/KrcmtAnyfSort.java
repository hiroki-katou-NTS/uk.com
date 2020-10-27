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
 * The Class KrcmtAnyfSort.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_ANYF_SORT")
public class KrcmtAnyfSort extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst formula disporder PK. */
	@EmbeddedId
	protected KrcmtAnyfSortPK krcmtAnyfSortPK;

	/** The disporder. */
	@Column(name = "DISPORDER")
	private int disporder;

	/**
	 * Instantiates a new krcst formula disporder.
	 */
	public KrcmtAnyfSort() {
		super();
	}

	/**
	 * Instantiates a new krcst formula disporder.
	 *
	 * @param krcmtAnyfSortPK
	 *            the krcst formula disporder PK
	 */
	public KrcmtAnyfSort(KrcmtAnyfSortPK krcmtAnyfSortPK) {
		this.krcmtAnyfSortPK = krcmtAnyfSortPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtAnyfSortPK != null ? krcmtAnyfSortPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtAnyfSort)) {
			return false;
		}
		KrcmtAnyfSort other = (KrcmtAnyfSort) object;
		if ((this.krcmtAnyfSortPK == null && other.krcmtAnyfSortPK != null)
				|| (this.krcmtAnyfSortPK != null
						&& !this.krcmtAnyfSortPK.equals(other.krcmtAnyfSortPK))) {
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
		return this.krcmtAnyfSortPK;
	}

}
