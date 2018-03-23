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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstFlexSet;

/**
 * The Class KshstEmpFlexSet.
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHST_EMP_FLEX_SET")
public class KshstEmpFlexSet extends KshstFlexSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst emp flex set PK. */
	@EmbeddedId
	protected KshstEmpFlexSetPK kshstEmpFlexSetPK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstEmpFlexSetPK != null ? kshstEmpFlexSetPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstEmpFlexSet)) {
			return false;
		}
		KshstEmpFlexSet other = (KshstEmpFlexSet) object;
		if ((this.kshstEmpFlexSetPK == null && other.kshstEmpFlexSetPK != null)
				|| (this.kshstEmpFlexSetPK != null && !this.kshstEmpFlexSetPK.equals(other.kshstEmpFlexSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstEmpFlexSetPK;
	}

}
