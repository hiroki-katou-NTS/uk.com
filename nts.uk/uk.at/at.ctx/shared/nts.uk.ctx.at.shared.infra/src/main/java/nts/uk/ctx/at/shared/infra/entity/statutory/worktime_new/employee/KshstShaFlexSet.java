/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstFlexSet;

/**
 * The Class KshstShaFlexSet.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHST_SHA_FLEX_SET")
public class KshstShaFlexSet extends KshstFlexSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst sha flex set PK. */
	@EmbeddedId
	protected KshstShaFlexSetPK kshstShaFlexSetPK;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstShaFlexSetPK != null ? kshstShaFlexSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstShaFlexSet)) {
			return false;
		}
		KshstShaFlexSet other = (KshstShaFlexSet) object;
		if ((this.kshstShaFlexSetPK == null && other.kshstShaFlexSetPK != null)
				|| (this.kshstShaFlexSetPK != null && !this.kshstShaFlexSetPK.equals(other.kshstShaFlexSetPK))) {
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
		return this.kshstShaFlexSetPK;
	}

}
