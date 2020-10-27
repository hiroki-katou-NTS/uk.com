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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstRegLaborTime;

/**
 * The Class KshmtLegaltimeDRegSya.
 */

@Setter
@Getter
@Entity
@Table(name = "KSHMT_LEGALTIME_D_REG_SYA")
@NoArgsConstructor
public class KshmtLegaltimeDRegSya extends KshstRegLaborTime implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst sha reg labor time PK. */
	@EmbeddedId
	protected KshmtLegaltimeDRegSyaPK kshmtLegaltimeDRegSyaPK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtLegaltimeDRegSyaPK != null ? kshmtLegaltimeDRegSyaPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtLegaltimeDRegSya)) {
			return false;
		}
		KshmtLegaltimeDRegSya other = (KshmtLegaltimeDRegSya) object;
		if ((this.kshmtLegaltimeDRegSyaPK == null && other.kshmtLegaltimeDRegSyaPK != null)
				|| (this.kshmtLegaltimeDRegSyaPK != null
						&& !this.kshmtLegaltimeDRegSyaPK.equals(other.kshmtLegaltimeDRegSyaPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtLegaltimeDRegSyaPK;
	}

}
