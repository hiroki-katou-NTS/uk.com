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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstNormalSet;

/**
 * The Class KshstWkpNormalSet.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHST_WKP_NORMAL_SET")
public class KshstWkpNormalSet extends KshstNormalSet implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst wkp normal set PK. */
	@EmbeddedId
	protected KshstWkpNormalSetPK kshstWkpNormalSetPK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstWkpNormalSetPK != null ? kshstWkpNormalSetPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstWkpNormalSet)) {
			return false;
		}
		KshstWkpNormalSet other = (KshstWkpNormalSet) object;
		if ((this.kshstWkpNormalSetPK == null && other.kshstWkpNormalSetPK != null)
				|| (this.kshstWkpNormalSetPK != null && !this.kshstWkpNormalSetPK.equals(other.kshstWkpNormalSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstWkpNormalSetPK;
	}

}
