/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.attendance;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The Class KshstOverTimeBrdAten.
 */
@Entity
@Table(name = "KSHST_OVER_TIME_BRD_ATEN")
public class KshstOverTimeBrdAten implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KshstOverTimeBrdAtenPK kshstOverTimeBrdAtenPK;

    public KshstOverTimeBrdAten() {
    }

    public KshstOverTimeBrdAten(KshstOverTimeBrdAtenPK kshstOverTimeBrdAtenPK) {
        this.kshstOverTimeBrdAtenPK = kshstOverTimeBrdAtenPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstOverTimeBrdAtenPK != null ? kshstOverTimeBrdAtenPK.hashCode() : 0);
        return hash;
    }

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstOverTimeBrdAten)) {
			return false;
		}
		KshstOverTimeBrdAten other = (KshstOverTimeBrdAten) object;
		if ((this.kshstOverTimeBrdAtenPK == null && other.kshstOverTimeBrdAtenPK != null)
				|| (this.kshstOverTimeBrdAtenPK != null
						&& !this.kshstOverTimeBrdAtenPK.equals(other.kshstOverTimeBrdAtenPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.KshstOverTimeBrdAten[ kshstOverTimeBrdAtenPK=" + kshstOverTimeBrdAtenPK
				+ " ]";
	}

}
