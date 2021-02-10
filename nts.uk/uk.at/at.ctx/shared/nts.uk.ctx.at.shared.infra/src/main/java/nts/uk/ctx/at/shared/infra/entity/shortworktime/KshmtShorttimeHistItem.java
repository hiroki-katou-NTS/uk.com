/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.shortworktime;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtShorttimeHistItem.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_SHORTTIME_HIST_ITEM")
public class KshmtShorttimeHistItem extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The bshmt worktime hist item PK. */
	@EmbeddedId
	protected BshmtWorktimeHistItemPK bshmtWorktimeHistItemPK;

	/** The child care atr. */
	@Column(name = "CHILD_CARE_ATR")
	private Integer childCareAtr;
	
	/** The lst bshmt schild care frame. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bshmtWorktimeHistItem", orphanRemoval = true)
	private List<KshmtShorttimeTs> lstBshmtSchildCareFrame;

	/**
	 * Instantiates a new bshmt worktime hist item.
	 */
	public KshmtShorttimeHistItem() {
		super();
	}

	/**
	 * Instantiates a new bshmt worktime hist item.
	 *
	 * @param bshmtWorktimeHistItemPK
	 *            the bshmt worktime hist item PK
	 */
	public KshmtShorttimeHistItem(BshmtWorktimeHistItemPK bshmtWorktimeHistItemPK) {
		this.bshmtWorktimeHistItemPK = bshmtWorktimeHistItemPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (bshmtWorktimeHistItemPK != null ? bshmtWorktimeHistItemPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtShorttimeHistItem)) {
			return false;
		}
		KshmtShorttimeHistItem other = (KshmtShorttimeHistItem) object;
		if ((this.bshmtWorktimeHistItemPK == null && other.bshmtWorktimeHistItemPK != null)
				|| (this.bshmtWorktimeHistItemPK != null
						&& !this.bshmtWorktimeHistItemPK.equals(other.bshmtWorktimeHistItemPK))) {
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
		return this.bshmtWorktimeHistItemPK;
	}

}
