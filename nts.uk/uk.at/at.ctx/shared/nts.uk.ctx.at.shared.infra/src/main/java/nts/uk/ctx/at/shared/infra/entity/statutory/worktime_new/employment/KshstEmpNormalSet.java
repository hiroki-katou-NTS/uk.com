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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstNormalSet;

/**
 * The Class KshstEmpNormalSet.
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHST_EMP_NORMAL_SET")
public class KshstEmpNormalSet extends KshstNormalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst emp normal set PK. */
	@EmbeddedId
	protected KshstEmpNormalSetPK kshstEmpNormalSetPK;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstEmpNormalSetPK != null ? kshstEmpNormalSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstEmpNormalSet)) {
			return false;
		}
		KshstEmpNormalSet other = (KshstEmpNormalSet) object;
		if ((this.kshstEmpNormalSetPK == null && other.kshstEmpNormalSetPK != null)
				|| (this.kshstEmpNormalSetPK != null && !this.kshstEmpNormalSetPK.equals(other.kshstEmpNormalSetPK))) {
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
		return this.kshstEmpNormalSetPK;
	}

}
