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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstTransLabTime;

/**
 * The Class KshmtLegaltimeDDefEmp.
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_LEGALTIME_D_DEF_EMP")
public class KshmtLegaltimeDDefEmp extends KshstTransLabTime implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst emp trans lab time PK. */
	@EmbeddedId
	protected KshmtLegaltimeDDefEmpPK kshmtLegaltimeDDefEmpPK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtLegaltimeDDefEmpPK != null ? kshmtLegaltimeDDefEmpPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtLegaltimeDDefEmp)) {
			return false;
		}
		KshmtLegaltimeDDefEmp other = (KshmtLegaltimeDDefEmp) object;
		if ((this.kshmtLegaltimeDDefEmpPK == null && other.kshmtLegaltimeDDefEmpPK != null)
				|| (this.kshmtLegaltimeDDefEmpPK != null
						&& !this.kshmtLegaltimeDDefEmpPK.equals(other.kshmtLegaltimeDDefEmpPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtLegaltimeDDefEmpPK;
	}

}
