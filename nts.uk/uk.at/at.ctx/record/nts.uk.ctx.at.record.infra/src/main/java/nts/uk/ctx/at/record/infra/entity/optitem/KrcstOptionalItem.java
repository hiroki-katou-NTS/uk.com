/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KrcstOptionalItem.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_OPTIONAL_ITEM")
public class KrcstOptionalItem extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst optional item PK. */
	@EmbeddedId
	protected KrcstOptionalItemPK krcstOptionalItemPK;

	/** The optional item name. */
	@Column(name = "OPTIONAL_ITEM_NAME")
	private String optionalItemName;

	/** The optional item atr. */
	@Column(name = "OPTIONAL_ITEM_ATR")
	private int optionalItemAtr;

	/** The usage atr. */
	@Column(name = "USAGE_ATR")
	private int usageAtr;

	/** The performance atr. */
	@Column(name = "PERFORMANCE_ATR")
	private int performanceAtr;

	/** The emp condition atr. */
	@Column(name = "EMP_CONDITION_ATR")
	private int empConditionAtr;

	/** The unit of optional item. */
	@Column(name = "UNIT_OF_OPTIONAL_ITEM")
	private String unitOfOptionalItem;

	/** The krcst calc result range. */
	@OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "OPTIONAL_ITEM_NO", referencedColumnName = "OPTIONAL_ITEM_NO") })
	public KrcstCalcResultRange krcstCalcResultRange;
	
	/**
	 * Instantiates a new krcst optional item.
	 */
	public KrcstOptionalItem() {
		super();
	}

	/**
	 * Instantiates a new krcst optional item.
	 *
	 * @param krcstOptionalItemPK
	 *            the krcst optional item PK
	 */
	public KrcstOptionalItem(KrcstOptionalItemPK krcstOptionalItemPK) {
		this.krcstOptionalItemPK = krcstOptionalItemPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcstOptionalItemPK != null ? krcstOptionalItemPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcstOptionalItem)) {
			return false;
		}
		KrcstOptionalItem other = (KrcstOptionalItem) object;
		if ((this.krcstOptionalItemPK == null && other.krcstOptionalItemPK != null)
				|| (this.krcstOptionalItemPK != null
						&& !this.krcstOptionalItemPK.equals(other.krcstOptionalItemPK))) {
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
		return this.krcstOptionalItemPK;
	}

}
