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
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstDeforMCalSet;

/**
 * The Class KrcstEmpDeforMCalSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_EMP_DEFOR_M_CAL_SET")
public class KrcstEmpDeforMCalSet extends KrcstDeforMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst emp defor M cal set PK. */
	@EmbeddedId
	protected KrcstEmpDeforMCalSetPK krcstEmpDeforMCalSetPK;

	/**
	 * Instantiates a new krcst emp defor M cal set.
	 */
	public KrcstEmpDeforMCalSet() {
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
		hash += (krcstEmpDeforMCalSetPK != null ? krcstEmpDeforMCalSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcstEmpDeforMCalSet)) {
			return false;
		}
		KrcstEmpDeforMCalSet other = (KrcstEmpDeforMCalSet) object;
		if ((this.krcstEmpDeforMCalSetPK == null && other.krcstEmpDeforMCalSetPK != null)
				|| (this.krcstEmpDeforMCalSetPK != null
						&& !this.krcstEmpDeforMCalSetPK.equals(other.krcstEmpDeforMCalSetPK))) {
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
		return this.krcstEmpDeforMCalSetPK;
	}

}
