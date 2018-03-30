/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstTransLabTime;

/**
 * The Class KshstWkpTransLabTime.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHST_WKP_TRANS_LAB_TIME")
public class KshstWkpTransLabTime extends KshstTransLabTime implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst wkp trans lab time PK. */
	@EmbeddedId
	protected KshstWkpTransLabTimePK kshstWkpTransLabTimePK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstWkpTransLabTimePK != null ? kshstWkpTransLabTimePK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstWkpTransLabTime)) {
			return false;
		}
		KshstWkpTransLabTime other = (KshstWkpTransLabTime) object;
		if ((this.kshstWkpTransLabTimePK == null && other.kshstWkpTransLabTimePK != null)
				|| (this.kshstWkpTransLabTimePK != null
						&& !this.kshstWkpTransLabTimePK.equals(other.kshstWkpTransLabTimePK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstWkpTransLabTimePK;
	}

}
