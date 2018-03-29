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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstFlexSet;

/**
 * The Class KshstComFlexSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_COM_FLEX_SET")
public class KshstComFlexSet extends KshstFlexSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst com flex set PK. */
	@EmbeddedId
	protected KshstComFlexSetPK kshstComFlexSetPK;

	/**
	 * Instantiates a new kshst com flex set.
	 */
	public KshstComFlexSet() {
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
		hash += (kshstComFlexSetPK != null ? kshstComFlexSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstComFlexSet)) {
			return false;
		}
		KshstComFlexSet other = (KshstComFlexSet) object;
		if ((this.kshstComFlexSetPK == null && other.kshstComFlexSetPK != null)
				|| (this.kshstComFlexSetPK != null
						&& !this.kshstComFlexSetPK.equals(other.kshstComFlexSetPK))) {
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
		return this.kshstComFlexSetPK;
	}

}
