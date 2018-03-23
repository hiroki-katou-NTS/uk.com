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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstDeforLarSet;

/**
 * The Class KshstShaDeforLarSet.
 */

@Setter
@Getter
@Entity
@Table(name = "KSHST_SHA_DEFOR_LAR_SET")
@NoArgsConstructor
public class KshstShaDeforLarSet extends KshstDeforLarSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst sha defor lar set PK. */
	@EmbeddedId
	protected KshstShaDeforLarSetPK kshstShaDeforLarSetPK;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstShaDeforLarSetPK != null ? kshstShaDeforLarSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstShaDeforLarSet)) {
			return false;
		}
		KshstShaDeforLarSet other = (KshstShaDeforLarSet) object;
		if ((this.kshstShaDeforLarSetPK == null && other.kshstShaDeforLarSetPK != null)
				|| (this.kshstShaDeforLarSetPK != null
						&& !this.kshstShaDeforLarSetPK.equals(other.kshstShaDeforLarSetPK))) {
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
		return this.kshstShaDeforLarSetPK;
	}

}
