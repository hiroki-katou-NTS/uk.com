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
 * The Class KshstEmpRegLaborTime.
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHST_EMP_REG_LABOR_TIME")
public class KshstEmpRegLaborTime extends KshstRegLaborTime implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst emp reg labor time PK. */
	@EmbeddedId
	protected KshstEmpRegLaborTimePK kshstEmpRegLaborTimePK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstEmpRegLaborTimePK != null ? kshstEmpRegLaborTimePK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstEmpRegLaborTime)) {
			return false;
		}
		KshstEmpRegLaborTime other = (KshstEmpRegLaborTime) object;
		if ((this.kshstEmpRegLaborTimePK == null && other.kshstEmpRegLaborTimePK != null)
				|| (this.kshstEmpRegLaborTimePK != null
						&& !this.kshstEmpRegLaborTimePK.equals(other.kshstEmpRegLaborTimePK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstEmpRegLaborTimePK;
	}

}
