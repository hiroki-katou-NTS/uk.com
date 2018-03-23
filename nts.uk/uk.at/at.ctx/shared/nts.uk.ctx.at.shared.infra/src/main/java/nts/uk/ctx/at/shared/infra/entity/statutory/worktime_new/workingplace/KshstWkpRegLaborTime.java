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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstRegLaborTime;

/**
 * The Class KshstWkpRegLaborTime.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHST_WKP_REG_LABOR_TIME")
public class KshstWkpRegLaborTime extends KshstRegLaborTime implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst wkp reg labor time PK. */
	@EmbeddedId
	protected KshstWkpRegLaborTimePK kshstWkpRegLaborTimePK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstWkpRegLaborTimePK != null ? kshstWkpRegLaborTimePK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstWkpRegLaborTime)) {
			return false;
		}
		KshstWkpRegLaborTime other = (KshstWkpRegLaborTime) object;
		if ((this.kshstWkpRegLaborTimePK == null && other.kshstWkpRegLaborTimePK != null)
				|| (this.kshstWkpRegLaborTimePK != null
						&& !this.kshstWkpRegLaborTimePK.equals(other.kshstWkpRegLaborTimePK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstWkpRegLaborTimePK;
	}

}
