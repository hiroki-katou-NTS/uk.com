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
 * The Class KshstOutsideOtBrdAten.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHST_OUTSIDE_OT_BRD_ATEN")
public class KshstOutsideOtBrdAten extends JpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KshstOutsideOtBrdAtenPK kshstOutsideOtBrdAtenPK;

    /**
     * Instantiates a new kshst over time brd aten.
     */
    public KshstOutsideOtBrdAten() {
    }

    /**
     * Instantiates a new kshst over time brd aten.
     *
     * @param kshstOverTimeBrdAtenPK the kshst over time brd aten PK
     */
    public KshstOutsideOtBrdAten(KshstOutsideOtBrdAtenPK kshstOverTimeBrdAtenPK) {
        this.kshstOutsideOtBrdAtenPK = kshstOverTimeBrdAtenPK;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstOutsideOtBrdAtenPK != null ? kshstOutsideOtBrdAtenPK.hashCode() : 0);
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
		if (!(object instanceof KshstOutsideOtBrdAten)) {
			return false;
		}
		KshstOutsideOtBrdAten other = (KshstOutsideOtBrdAten) object;
		if ((this.kshstOutsideOtBrdAtenPK == null && other.kshstOutsideOtBrdAtenPK != null)
				|| (this.kshstOutsideOtBrdAtenPK != null
						&& !this.kshstOutsideOtBrdAtenPK.equals(other.kshstOutsideOtBrdAtenPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshstOverTimeBrdAten[ kshstOverTimeBrdAtenPK=" + kshstOutsideOtBrdAtenPK
				+ " ]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstOutsideOtBrdAtenPK;
	}

}
