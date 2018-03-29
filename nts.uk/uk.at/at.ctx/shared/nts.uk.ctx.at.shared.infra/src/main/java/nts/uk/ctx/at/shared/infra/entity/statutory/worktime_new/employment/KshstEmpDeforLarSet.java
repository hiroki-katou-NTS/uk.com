/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstDeforLarSet;

/**
 * The Class KshstEmpDeforLarSet.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHST_EMP_DEFOR_LAR_SET")
public class KshstEmpDeforLarSet extends KshstDeforLarSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst emp defor lar set PK. */
	@EmbeddedId
	protected KshstEmpDeforLarSetPK kshstEmpDeforLarSetPK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstEmpDeforLarSetPK != null ? kshstEmpDeforLarSetPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstEmpDeforLarSet)) {
			return false;
		}
		KshstEmpDeforLarSet other = (KshstEmpDeforLarSet) object;
		if ((this.kshstEmpDeforLarSetPK == null && other.kshstEmpDeforLarSetPK != null)
				|| (this.kshstEmpDeforLarSetPK != null
						&& !this.kshstEmpDeforLarSetPK.equals(other.kshstEmpDeforLarSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstEmpDeforLarSetPK;
	}

}
