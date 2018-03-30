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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstTransLabTime;

/**
 * The Class KshstShaTransLabTime.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHST_SHA_TRANS_LAB_TIME")
public class KshstShaTransLabTime extends KshstTransLabTime implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst sha trans lab time PK. */
	@EmbeddedId
	protected KshstShaTransLabTimePK kshstShaTransLabTimePK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstShaTransLabTimePK != null ? kshstShaTransLabTimePK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstShaTransLabTime)) {
			return false;
		}
		KshstShaTransLabTime other = (KshstShaTransLabTime) object;
		if ((this.kshstShaTransLabTimePK == null && other.kshstShaTransLabTimePK != null)
				|| (this.kshstShaTransLabTimePK != null
						&& !this.kshstShaTransLabTimePK.equals(other.kshstShaTransLabTimePK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstShaTransLabTimePK;
	}

}
