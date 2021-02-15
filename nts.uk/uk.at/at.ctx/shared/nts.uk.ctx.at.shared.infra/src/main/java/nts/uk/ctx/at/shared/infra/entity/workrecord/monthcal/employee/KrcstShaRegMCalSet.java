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
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstRegMCalSet;

/**
 * The Class KrcstShaRegMCalSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_SHA_REG_M_CAL_SET")
public class KrcstShaRegMCalSet extends KrcstRegMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst sha reg M cal set PK. */
	@EmbeddedId
	protected KrcstShaRegMCalSetPK krcstShaRegMCalSetPK;

	/**
	 * Instantiates a new krcst sha reg M cal set.
	 */
	public KrcstShaRegMCalSet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcstShaRegMCalSetPK != null ? krcstShaRegMCalSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcstShaRegMCalSet)) {
			return false;
		}
		KrcstShaRegMCalSet other = (KrcstShaRegMCalSet) object;
		if ((this.krcstShaRegMCalSetPK == null && other.krcstShaRegMCalSetPK != null)
				|| (this.krcstShaRegMCalSetPK != null
						&& !this.krcstShaRegMCalSetPK.equals(other.krcstShaRegMCalSetPK))) {
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
		return this.krcstShaRegMCalSetPK;
	}

}
