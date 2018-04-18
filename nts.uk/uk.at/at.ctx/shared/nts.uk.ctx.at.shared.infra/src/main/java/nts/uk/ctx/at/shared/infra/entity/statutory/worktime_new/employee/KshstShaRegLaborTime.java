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
 * The Class KshstShaRegLaborTime.
 */

@Setter
@Getter
@Entity
@Table(name = "KSHST_SHA_REG_LABOR_TIME")
@NoArgsConstructor
public class KshstShaRegLaborTime extends KshstRegLaborTime implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst sha reg labor time PK. */
	@EmbeddedId
	protected KshstShaRegLaborTimePK kshstShaRegLaborTimePK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstShaRegLaborTimePK != null ? kshstShaRegLaborTimePK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstShaRegLaborTime)) {
			return false;
		}
		KshstShaRegLaborTime other = (KshstShaRegLaborTime) object;
		if ((this.kshstShaRegLaborTimePK == null && other.kshstShaRegLaborTimePK != null)
				|| (this.kshstShaRegLaborTimePK != null
						&& !this.kshstShaRegLaborTimePK.equals(other.kshstShaRegLaborTimePK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstShaRegLaborTimePK;
	}

}
