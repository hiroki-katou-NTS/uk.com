/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.KrcstDeforMCalSet;

/**
 * The Class KrcstShaDeforMCalSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_SHA_DEFOR_M_CAL_SET")
public class KrcstShaDeforMCalSet extends KrcstDeforMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst sha defor M cal set PK. */
	@EmbeddedId
	protected KrcstShaDeforMCalSetPK krcstShaDeforMCalSetPK;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcstShaDeforMCalSetPK != null ? krcstShaDeforMCalSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KrcstShaDeforMCalSet)) {
			return false;
		}
		KrcstShaDeforMCalSet other = (KrcstShaDeforMCalSet) object;
		if ((this.krcstShaDeforMCalSetPK == null && other.krcstShaDeforMCalSetPK != null)
				|| (this.krcstShaDeforMCalSetPK != null
						&& !this.krcstShaDeforMCalSetPK.equals(other.krcstShaDeforMCalSetPK))) {
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
		return this.krcstShaDeforMCalSetPK;
	}

}
