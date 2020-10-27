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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstRegLaborTime;

/**
 * The Class KshmtLegaltimeDRegEmp.
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_LEGALTIME_D_REG_EMP")
public class KshmtLegaltimeDRegEmp extends KshstRegLaborTime implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst emp reg labor time PK. */
	@EmbeddedId
	protected KshmtLegaltimeDRegEmpPK kshmtLegaltimeDRegEmpPK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtLegaltimeDRegEmpPK != null ? kshmtLegaltimeDRegEmpPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtLegaltimeDRegEmp)) {
			return false;
		}
		KshmtLegaltimeDRegEmp other = (KshmtLegaltimeDRegEmp) object;
		if ((this.kshmtLegaltimeDRegEmpPK == null && other.kshmtLegaltimeDRegEmpPK != null)
				|| (this.kshmtLegaltimeDRegEmpPK != null
						&& !this.kshmtLegaltimeDRegEmpPK.equals(other.kshmtLegaltimeDRegEmpPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtLegaltimeDRegEmpPK;
	}

}
