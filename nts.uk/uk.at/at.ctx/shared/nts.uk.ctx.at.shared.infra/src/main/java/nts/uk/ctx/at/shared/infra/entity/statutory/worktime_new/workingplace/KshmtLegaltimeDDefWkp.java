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
 * The Class KshmtLegaltimeDDefWkp.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_LEGALTIME_D_DEF_WKP")
public class KshmtLegaltimeDDefWkp extends KshstTransLabTime implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst wkp trans lab time PK. */
	@EmbeddedId
	protected KshmtLegaltimeDDefWkpPK kshmtLegaltimeDDefWkpPK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtLegaltimeDDefWkpPK != null ? kshmtLegaltimeDDefWkpPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtLegaltimeDDefWkp)) {
			return false;
		}
		KshmtLegaltimeDDefWkp other = (KshmtLegaltimeDDefWkp) object;
		if ((this.kshmtLegaltimeDDefWkpPK == null && other.kshmtLegaltimeDDefWkpPK != null)
				|| (this.kshmtLegaltimeDDefWkpPK != null
						&& !this.kshmtLegaltimeDDefWkpPK.equals(other.kshmtLegaltimeDDefWkpPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtLegaltimeDDefWkpPK;
	}

}
