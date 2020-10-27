/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtClosure.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_CLOSURE")
public class KshmtClosure extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kclmt closure PK. */
    @EmbeddedId
    protected KshmtClosurePK kshmtClosurePK;
    
    /** The use class. */
    @Column(name = "USE_ATR")
    private Integer useClass;
    
    /** The closure month. */
    @Column(name = "CLOSURE_MONTH")
    private Integer closureMonth;

    /**
     * Instantiates a new kclmt closure.
     */
    public KshmtClosure() {
    }

    /**
     * Instantiates a new kclmt closure.
     *
     * @param kshmtClosurePK the kclmt closure PK
     */
    public KshmtClosure(KshmtClosurePK kshmtClosurePK) {
        this.kshmtClosurePK = kshmtClosurePK;
    }


    /**
     * Instantiates a new kclmt closure.
     *
     * @param ccid the ccid
     * @param closureId the closure id
     */
    public KshmtClosure(String ccid, int closureId) {
        this.kshmtClosurePK = new KshmtClosurePK(ccid, closureId);
    }

    /**
     * Gets the kclmt closure PK.
     *
     * @return the kclmt closure PK
     */
    public KshmtClosurePK getKshmtClosurePK() {
        return kshmtClosurePK;
    }

    /**
     * Sets the kclmt closure PK.
     *
     * @param kshmtClosurePK the new kclmt closure PK
     */
    public void setKshmtClosurePK(KshmtClosurePK kshmtClosurePK) {
        this.kshmtClosurePK = kshmtClosurePK;
    }


    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtClosurePK != null ? kshmtClosurePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtClosure)) {
            return false;
        }
		KshmtClosure other = (KshmtClosure) object;
		if ((this.kshmtClosurePK == null && other.kshmtClosurePK != null)
			|| (this.kshmtClosurePK != null && !this.kshmtClosurePK.equals(other.kshmtClosurePK))) {
			return false;
		}
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtClosure[ kshmtClosurePK=" + kshmtClosurePK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getKshmtClosurePK();
	}
    
}
