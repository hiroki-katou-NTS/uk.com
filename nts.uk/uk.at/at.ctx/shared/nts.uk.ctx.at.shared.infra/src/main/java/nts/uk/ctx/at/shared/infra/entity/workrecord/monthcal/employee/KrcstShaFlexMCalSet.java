/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstFlexMCalSet;

/**
 * The Class KrcstShaFlexMCalSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_SHA_FLEX_M_CAL_SET")
public class KrcstShaFlexMCalSet extends KrcstFlexMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst sha flex M cal set PK. */
	@EmbeddedId
	protected KrcstShaFlexMCalSetPK krcstShaFlexMCalSetPK;

	/**
	 * Instantiates a new krcst sha flex M cal set.
	 */
	public KrcstShaFlexMCalSet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcstShaFlexMCalSetPK != null ? krcstShaFlexMCalSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcstShaFlexMCalSet)) {
			return false;
		}
		KrcstShaFlexMCalSet other = (KrcstShaFlexMCalSet) object;
		if ((this.krcstShaFlexMCalSetPK == null && other.krcstShaFlexMCalSetPK != null)
				|| (this.krcstShaFlexMCalSetPK != null
						&& !this.krcstShaFlexMCalSetPK.equals(other.krcstShaFlexMCalSetPK))) {
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
		return this.krcstShaFlexMCalSetPK;
	}

}
