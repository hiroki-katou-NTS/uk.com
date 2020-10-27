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
 * The Class KrcmtCalcSetWkp.
 */
@Setter
@Getter
@Entity
@Table(name = "KRCMT_CALC_SET_WKP")
public class KrcmtCalcSetWkp extends KshmtAutoCalSet implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt auto wkp cal set PK. */
    @EmbeddedId
    protected KrcmtCalcSetWkpPK krcmtCalcSetWkpPK;

    /**
     * Instantiates a new kshmt auto wkp cal set.
     */
    public KrcmtCalcSetWkp() {
    	super();
    }

    /**
     * Instantiates a new kshmt auto wkp cal set.
     *
     * @param krcmtCalcSetWkpPK the kshmt auto wkp cal set PK
     */
    public KrcmtCalcSetWkp(KrcmtCalcSetWkpPK krcmtCalcSetWkpPK) {
        this.krcmtCalcSetWkpPK = krcmtCalcSetWkpPK;
    }

    /**
     * Instantiates a new kshmt auto wkp cal set.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     */
    public KrcmtCalcSetWkp(String cid, String wkpid) {
        this.krcmtCalcSetWkpPK = new KrcmtCalcSetWkpPK(cid, wkpid);
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (krcmtCalcSetWkpPK != null ? krcmtCalcSetWkpPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KrcmtCalcSetWkp)) {
            return false;
        }
        KrcmtCalcSetWkp other = (KrcmtCalcSetWkp) object;
        if ((this.krcmtCalcSetWkpPK == null && other.krcmtCalcSetWkpPK != null) || (this.krcmtCalcSetWkpPK != null && !this.krcmtCalcSetWkpPK.equals(other.krcmtCalcSetWkpPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.krcmtCalcSetWkpPK;
	}
    
}
