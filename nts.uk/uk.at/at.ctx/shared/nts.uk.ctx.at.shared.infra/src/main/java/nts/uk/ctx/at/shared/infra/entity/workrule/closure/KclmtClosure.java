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
 * The Class KclmtClosure.
 */
@Getter
@Setter
@Entity
@Table(name = "KCLMT_CLOSURE")
public class KclmtClosure extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kclmt closure PK. */
    @EmbeddedId
    protected KclmtClosurePK kclmtClosurePK;
    
    /** The use class. */
    @Column(name = "USE_ATR")
    private Integer useClass;
    
    /** The closure month. */
    @Column(name = "CLOSURE_MONTH")
    private Integer closureMonth;

    /**
     * Instantiates a new kclmt closure.
     */
    public KclmtClosure() {
    }

    /**
     * Instantiates a new kclmt closure.
     *
     * @param kclmtClosurePK the kclmt closure PK
     */
    public KclmtClosure(KclmtClosurePK kclmtClosurePK) {
        this.kclmtClosurePK = kclmtClosurePK;
    }


    /**
     * Instantiates a new kclmt closure.
     *
     * @param ccid the ccid
     * @param closureId the closure id
     */
    public KclmtClosure(String ccid, int closureId) {
        this.kclmtClosurePK = new KclmtClosurePK(ccid, closureId);
    }

    /**
     * Gets the kclmt closure PK.
     *
     * @return the kclmt closure PK
     */
    public KclmtClosurePK getKclmtClosurePK() {
        return kclmtClosurePK;
    }

    /**
     * Sets the kclmt closure PK.
     *
     * @param kclmtClosurePK the new kclmt closure PK
     */
    public void setKclmtClosurePK(KclmtClosurePK kclmtClosurePK) {
        this.kclmtClosurePK = kclmtClosurePK;
    }


    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kclmtClosurePK != null ? kclmtClosurePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KclmtClosure)) {
            return false;
        }
		KclmtClosure other = (KclmtClosure) object;
		if ((this.kclmtClosurePK == null && other.kclmtClosurePK != null)
			|| (this.kclmtClosurePK != null && !this.kclmtClosurePK.equals(other.kclmtClosurePK))) {
			return false;
		}
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KclmtClosure[ kclmtClosurePK=" + kclmtClosurePK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getKclmtClosurePK();
	}
    
}
