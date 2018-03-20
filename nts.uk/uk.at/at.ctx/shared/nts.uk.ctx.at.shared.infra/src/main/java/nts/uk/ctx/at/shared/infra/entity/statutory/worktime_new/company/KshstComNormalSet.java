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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstNormalSet;

/**
 * The Class KshstComNormalSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_COM_NORMAL_SET")
public class KshstComNormalSet extends KshstNormalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst com normal set PK. */
	@EmbeddedId
	protected KshstComNormalSetPK kshstComNormalSetPK;

	/**
	 * Instantiates a new kshst com normal set.
	 */
	public KshstComNormalSet() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstComNormalSetPK != null ? kshstComNormalSetPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstComNormalSet)) {
			return false;
		}
		KshstComNormalSet other = (KshstComNormalSet) object;
		if ((this.kshstComNormalSetPK == null && other.kshstComNormalSetPK != null)
				|| (this.kshstComNormalSetPK != null && !this.kshstComNormalSetPK.equals(other.kshstComNormalSetPK))) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getKey() {
		return this.kshstComNormalSetPK;
	}
	
}
