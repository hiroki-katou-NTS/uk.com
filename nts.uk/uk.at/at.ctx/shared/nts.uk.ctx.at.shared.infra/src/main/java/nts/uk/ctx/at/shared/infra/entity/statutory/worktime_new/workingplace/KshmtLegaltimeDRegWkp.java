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
 * The Class KshmtLegaltimeDRegWkp.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_LEGALTIME_D_REG_WKP")
public class KshmtLegaltimeDRegWkp extends KshstRegLaborTime implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kshst wkp reg labor time PK. */
	@EmbeddedId
	protected KshmtLegaltimeDRegWkpPK kshmtLegaltimeDRegWkpPK;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtLegaltimeDRegWkpPK != null ? kshmtLegaltimeDRegWkpPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtLegaltimeDRegWkp)) {
			return false;
		}
		KshmtLegaltimeDRegWkp other = (KshmtLegaltimeDRegWkp) object;
		if ((this.kshmtLegaltimeDRegWkpPK == null && other.kshmtLegaltimeDRegWkpPK != null)
				|| (this.kshmtLegaltimeDRegWkpPK != null
						&& !this.kshmtLegaltimeDRegWkpPK.equals(other.kshmtLegaltimeDRegWkpPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtLegaltimeDRegWkpPK;
	}

}
