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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstFlexSet;

/**
 * The Class KshstWkpFlexSet.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHST_WKP_FLEX_SET")
public class KshstWkpFlexSet extends KshstFlexSet implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst wkp flex set PK. */
	@EmbeddedId
	protected KshstWkpFlexSetPK kshstWkpFlexSetPK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstWkpFlexSetPK != null ? kshstWkpFlexSetPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstWkpFlexSet)) {
			return false;
		}
		KshstWkpFlexSet other = (KshstWkpFlexSet) object;
		if ((this.kshstWkpFlexSetPK == null && other.kshstWkpFlexSetPK != null)
				|| (this.kshstWkpFlexSetPK != null && !this.kshstWkpFlexSetPK.equals(other.kshstWkpFlexSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstWkpFlexSetPK;
	}
}
