/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.KshmtAutoCalSet;

/**
 * The Class KshmtAutoWkpCalSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_AUTO_WKP_CAL_SET")
public class KshmtAutoWkpCalSet extends KshmtAutoCalSet implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt auto wkp cal set PK. */
    @EmbeddedId
    protected KshmtAutoWkpCalSetPK kshmtAutoWkpCalSetPK;

    /**
     * Instantiates a new kshmt auto wkp cal set.
     */
    public KshmtAutoWkpCalSet() {
    	super();
    }

    /**
     * Instantiates a new kshmt auto wkp cal set.
     *
     * @param kshmtAutoWkpCalSetPK the kshmt auto wkp cal set PK
     */
    public KshmtAutoWkpCalSet(KshmtAutoWkpCalSetPK kshmtAutoWkpCalSetPK) {
        this.kshmtAutoWkpCalSetPK = kshmtAutoWkpCalSetPK;
    }

    /**
     * Instantiates a new kshmt auto wkp cal set.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     */
    public KshmtAutoWkpCalSet(String cid, String wkpid) {
        this.kshmtAutoWkpCalSetPK = new KshmtAutoWkpCalSetPK(cid, wkpid);
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtAutoWkpCalSetPK != null ? kshmtAutoWkpCalSetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtAutoWkpCalSet)) {
            return false;
        }
        KshmtAutoWkpCalSet other = (KshmtAutoWkpCalSet) object;
        if ((this.kshmtAutoWkpCalSetPK == null && other.kshmtAutoWkpCalSetPK != null) || (this.kshmtAutoWkpCalSetPK != null && !this.kshmtAutoWkpCalSetPK.equals(other.kshmtAutoWkpCalSetPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtAutoWkpCalSetPK;
	}
    
}
