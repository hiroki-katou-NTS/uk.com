/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.KshmtAutoCalSet;

/**
 * The Class KshmtAutoJobCalSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_AUTO_JOB_CAL_SET")
public class KshmtAutoJobCalSet extends KshmtAutoCalSet implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt auto job cal set PK. */
    @EmbeddedId
    protected KshmtAutoJobCalSetPK kshmtAutoJobCalSetPK;

    /**
     * Instantiates a new kshmt auto job cal set.
     */
    public KshmtAutoJobCalSet() {
    	super();
    }

    /**
     * Instantiates a new kshmt auto job cal set.
     *
     * @param kshmtAutoJobCalSetPK the kshmt auto job cal set PK
     */
    public KshmtAutoJobCalSet(KshmtAutoJobCalSetPK kshmtAutoJobCalSetPK) {
        this.kshmtAutoJobCalSetPK = kshmtAutoJobCalSetPK;
    }

    /**
     * Instantiates a new kshmt auto job cal set.
     *
     * @param cid the cid
     * @param jobid the jobid
     */
    public KshmtAutoJobCalSet(String cid, String jobid) {
        this.kshmtAutoJobCalSetPK = new KshmtAutoJobCalSetPK(cid, jobid);
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtAutoJobCalSetPK != null ? kshmtAutoJobCalSetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtAutoJobCalSet)) {
            return false;
        }
        KshmtAutoJobCalSet other = (KshmtAutoJobCalSet) object;
        if ((this.kshmtAutoJobCalSetPK == null && other.kshmtAutoJobCalSetPK != null) || (this.kshmtAutoJobCalSetPK != null && !this.kshmtAutoJobCalSetPK.equals(other.kshmtAutoJobCalSetPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtAutoJobCalSetPK;
	}
    
}
