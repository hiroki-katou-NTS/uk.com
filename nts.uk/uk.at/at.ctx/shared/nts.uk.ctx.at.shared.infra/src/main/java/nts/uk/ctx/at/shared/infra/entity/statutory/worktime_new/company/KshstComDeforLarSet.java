/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstDeforLarSet;

/**
 * The Class KshstComDeforLarSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_COM_DEFOR_LAR_SET")
public class KshstComDeforLarSet extends KshstDeforLarSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst com defor lar set PK. */
	@EmbeddedId
	protected KshstComDeforLarSetPK kshstComDeforLarSetPK;

	/**
	 * Instantiates a new kshst com defor lar set.
	 */
	public KshstComDeforLarSet() {
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
		hash += (kshstComDeforLarSetPK != null ? kshstComDeforLarSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstComDeforLarSet)) {
			return false;
		}
		KshstComDeforLarSet other = (KshstComDeforLarSet) object;
		if ((this.kshstComDeforLarSetPK == null && other.kshstComDeforLarSetPK != null)
				|| (this.kshstComDeforLarSetPK != null
						&& !this.kshstComDeforLarSetPK.equals(other.kshstComDeforLarSetPK))) {
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
		return this.kshstComDeforLarSetPK;
	}

}
