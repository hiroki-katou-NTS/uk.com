/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstFlexMCalSet;

/**
 * The Class KrcstEmpFlexMCalSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_EMP_FLEX_M_CAL_SET")
public class KrcstEmpFlexMCalSet extends KrcstFlexMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst emp flex M cal set PK. */
	@EmbeddedId
	protected KrcstEmpFlexMCalSetPK krcstEmpFlexMCalSetPK;

	/**
	 * Instantiates a new krcst emp flex M cal set.
	 */
	public KrcstEmpFlexMCalSet() {
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
		hash += (krcstEmpFlexMCalSetPK != null ? krcstEmpFlexMCalSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcstEmpFlexMCalSet)) {
			return false;
		}
		KrcstEmpFlexMCalSet other = (KrcstEmpFlexMCalSet) object;
		if ((this.krcstEmpFlexMCalSetPK == null && other.krcstEmpFlexMCalSetPK != null)
				|| (this.krcstEmpFlexMCalSetPK != null
						&& !this.krcstEmpFlexMCalSetPK.equals(other.krcstEmpFlexMCalSetPK))) {
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
		return this.krcstEmpFlexMCalSetPK;
	}

}
