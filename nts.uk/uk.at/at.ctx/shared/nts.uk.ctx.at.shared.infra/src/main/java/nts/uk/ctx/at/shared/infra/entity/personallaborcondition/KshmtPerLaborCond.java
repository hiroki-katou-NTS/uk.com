/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.personallaborcondition;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtPerLaborCond.
 */
@Entity
@Table(name = "KSHMT_PER_LABOR_COND")
public class KshmtPerLaborCond extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt per labor cond PK. */
    @EmbeddedId
    protected KshmtPerLaborCondPK kshmtPerLaborCondPK;
    
    /** The sched mgmt atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SCHED_MGMT_ATR")
    private int schedMgmtAtr;
    
    /** The hd add one day. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_ADD_ONE_DAY")
    private int hdAddOneDay;
    
    /** The hd add morning. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_ADD_MORNING")
    private int hdAddMorning;
    
    /** The hd add afternoon. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "HD_ADD_AFTERNOON")
    private int hdAddAfternoon;
    
    /** The auto emboss set atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUTO_EMBOSS_SET_ATR")
    private int autoEmbossSetAtr;

    /**
     * Instantiates a new kshmt per labor cond.
     */
    public KshmtPerLaborCond() {
    }

    /**
     * Instantiates a new kshmt per labor cond.
     *
     * @param kshmtPerLaborCondPK the kshmt per labor cond PK
     */
    public KshmtPerLaborCond(KshmtPerLaborCondPK kshmtPerLaborCondPK) {
        this.kshmtPerLaborCondPK = kshmtPerLaborCondPK;
    }


    /**
     * Instantiates a new kshmt per labor cond.
     *
     * @param sid the sid
     * @param startYmd the start ymd
     * @param endYmd the end ymd
     */
    public KshmtPerLaborCond(String sid, Date startYmd, Date endYmd) {
        this.kshmtPerLaborCondPK = new KshmtPerLaborCondPK(sid, startYmd, endYmd);
    }

    /**
     * Gets the kshmt per labor cond PK.
     *
     * @return the kshmt per labor cond PK
     */
    public KshmtPerLaborCondPK getKshmtPerLaborCondPK() {
        return kshmtPerLaborCondPK;
    }

    /**
     * Sets the kshmt per labor cond PK.
     *
     * @param kshmtPerLaborCondPK the new kshmt per labor cond PK
     */
    public void setKshmtPerLaborCondPK(KshmtPerLaborCondPK kshmtPerLaborCondPK) {
        this.kshmtPerLaborCondPK = kshmtPerLaborCondPK;
    }


    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtPerLaborCondPK != null ? kshmtPerLaborCondPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtPerLaborCond)) {
			return false;
		}
		KshmtPerLaborCond other = (KshmtPerLaborCond) object;
		if ((this.kshmtPerLaborCondPK == null && other.kshmtPerLaborCondPK != null)
				|| (this.kshmtPerLaborCondPK != null && !this.kshmtPerLaborCondPK.equals(other.kshmtPerLaborCondPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtPerLaborCond[ kshmtPerLaborCondPK=" + kshmtPerLaborCondPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtPerLaborCondPK;
	}
	
    
}
