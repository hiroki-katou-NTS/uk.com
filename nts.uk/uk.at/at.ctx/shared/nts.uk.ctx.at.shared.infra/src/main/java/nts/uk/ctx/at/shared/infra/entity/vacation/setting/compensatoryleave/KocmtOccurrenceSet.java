/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KocmtOccurrenceSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KOCMT_OCCURRENCE_SET")
public class KocmtOccurrenceSet extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The kocmt occurrence set PK. */
    @EmbeddedId
    protected KocmtOccurrenceSetPK kocmtOccurrenceSetPK;

    /** The transf type. */
    @Basic(optional = false)
    @Column(name = "TRANSF_TYPE")
    private Integer transfType;

    /** The one day time. */
    @Column(name = "ONE_DAY_TIME")
    private Long oneDayTime;

    /** The half day time. */
    @Column(name = "HALF_DAY_TIME")
    private Long halfDayTime;

    /** The certain time. */
    @Column(name = "CERTAIN_TIME")
    private Long certainTime;

    /** The use type. */
    @Basic(optional = false)
    @Column(name = "USE_TYPE")
    private Integer useType;

    /**
     * Instantiates a new kocmt occurrence set.
     */
    public KocmtOccurrenceSet() {
    }

    /**
     * Instantiates a new kocmt occurrence set.
     *
     * @param kocmtOccurrenceSetPK
     *            the kocmt occurrence set PK
     */
    public KocmtOccurrenceSet(KocmtOccurrenceSetPK kocmtOccurrenceSetPK) {
        this.kocmtOccurrenceSetPK = kocmtOccurrenceSetPK;
    }

    /**
     * Instantiates a new kocmt occurrence set.
     *
     * @param kocmtOccurrenceSetPK
     *            the kocmt occurrence set PK
     * @param transfType
     *            the transf type
     * @param useType
     *            the use type
     */
    public KocmtOccurrenceSet(KocmtOccurrenceSetPK kocmtOccurrenceSetPK, Integer transfType, Integer useType) {
        this.kocmtOccurrenceSetPK = kocmtOccurrenceSetPK;
        this.transfType = transfType;
        this.useType = useType;
    }

    /**
     * Instantiates a new kocmt occurrence set.
     *
     * @param cid
     *            the cid
     * @param occurrType
     *            the occurr type
     */
    public KocmtOccurrenceSet(String cid, Integer occurrType) {
        this.kocmtOccurrenceSetPK = new KocmtOccurrenceSetPK(cid, occurrType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kocmtOccurrenceSetPK != null ? kocmtOccurrenceSetPK.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KocmtOccurrenceSet)) {
            return false;
        }
        KocmtOccurrenceSet other = (KocmtOccurrenceSet) object;
        if ((this.kocmtOccurrenceSetPK == null && other.kocmtOccurrenceSetPK != null)
                || (this.kocmtOccurrenceSetPK != null
                        && !this.kocmtOccurrenceSetPK.equals(other.kocmtOccurrenceSetPK))) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.kocmtOccurrenceSetPK;
    }

}
