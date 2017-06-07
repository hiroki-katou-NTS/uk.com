/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.closure;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KclmtClosure.
 */
@Entity
@Table(name = "KCLMT_CLOSURE")
public class KclmtClosure extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kclmt closure PK. */
    @EmbeddedId
    protected KclmtClosurePK kclmtClosurePK;
    
    /** The use class. */
    @Column(name = "USE_CLASS")
    private String useClass;
    
    /** The month. */
    @Column(name = "MONTH")
    private Integer month;

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
    public KclmtClosure(String ccid, String closureId) {
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


    /**
     * Gets the use class.
     *
     * @return the use class
     */
    public String getUseClass() {
        return useClass;
    }

    /**
     * Sets the use class.
     *
     * @param useClass the new use class
     */
    public void setUseClass(String useClass) {
        this.useClass = useClass;
    }

    /**
     * Gets the month.
     *
     * @return the month
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * Sets the month.
     *
     * @param month the new month
     */
    public void setMonth(Integer month) {
        this.month = month;
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

	@Override
	protected Object getKey() {
		return this.getKclmtClosurePK();
	}
    
}
