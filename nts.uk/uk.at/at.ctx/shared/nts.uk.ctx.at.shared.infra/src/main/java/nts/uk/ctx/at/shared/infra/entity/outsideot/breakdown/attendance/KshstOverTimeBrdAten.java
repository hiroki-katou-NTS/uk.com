/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class KshstOverTimeBrdAten.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHST_OVER_TIME_BRD_ATEN")
public class KshstOverTimeBrdAten extends JpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KshstOverTimeBrdAtenPK kshstOverTimeBrdAtenPK;

    /**
     * Instantiates a new kshst over time brd aten.
     */
    public KshstOverTimeBrdAten() {
    }

    /**
     * Instantiates a new kshst over time brd aten.
     *
     * @param kshstOverTimeBrdAtenPK the kshst over time brd aten PK
     */
    public KshstOverTimeBrdAten(KshstOverTimeBrdAtenPK kshstOverTimeBrdAtenPK) {
        this.kshstOverTimeBrdAtenPK = kshstOverTimeBrdAtenPK;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstOverTimeBrdAtenPK != null ? kshstOverTimeBrdAtenPK.hashCode() : 0);
        return hash;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshstOverTimeBrdAten[ kshstOverTimeBrdAtenPK=" + kshstOverTimeBrdAtenPK
				+ " ]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstOverTimeBrdAtenPK;
	}

}
