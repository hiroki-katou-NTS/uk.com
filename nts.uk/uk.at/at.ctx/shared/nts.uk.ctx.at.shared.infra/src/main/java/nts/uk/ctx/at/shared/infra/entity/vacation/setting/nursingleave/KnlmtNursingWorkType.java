/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KmfmtWorkType.
 */
@Setter
@Getter
@Entity
@Table(name = "KNLMT_NURSING_WORK_TYPE")
public class KnlmtNursingWorkType extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The knlmtNursingWorkTypePK. */
    @EmbeddedId
    private KnlmtNursingWorkTypePK knlmtNursingWorkTypePK;
    
    /** The work type code. */
    @Column(name = "WORK_TYPE_CD")
    private String workTypeCode;
    
    /** The knlmt nursing leave set. */
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "CID", referencedColumnName = "KSHMT_HDNURSING_LEAVE.CID", insertable = false, updatable = false),
        @JoinColumn(name = "NURSING_TYPE", referencedColumnName = "KSHMT_HDNURSING_LEAVE.NURSING_TYPE", insertable = false, updatable = false)})
    private KshmtHdnursingLeave kshmtHdnursingLeave;

    /**
     * Instantiates a new kmfmt work type.
     */
    public KnlmtNursingWorkType() {
    }
    
    /**
     * Instantiates a new knlmt nursing work type.
     *
     * @param knlmtNursingWorkTypePK the knlmt nursing work type PK
     */
    public KnlmtNursingWorkType(KnlmtNursingWorkTypePK knlmtNursingWorkTypePK) {
        this.knlmtNursingWorkTypePK = knlmtNursingWorkTypePK;
    }
    
    /**
     * Instantiates a new knlmt nursing work type.
     *
     * @param knlmtNursingWorkTypePK the knlmt nursing work type PK
     * @param workTypeCode the work type code
     */
    public KnlmtNursingWorkType(KnlmtNursingWorkTypePK knlmtNursingWorkTypePK, String workTypeCode) {
        this.knlmtNursingWorkTypePK = knlmtNursingWorkTypePK;
        this.workTypeCode = workTypeCode;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (knlmtNursingWorkTypePK != null ? knlmtNursingWorkTypePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // not set
        if (!(object instanceof KnlmtNursingWorkType)) {
            return false;
        }
        KnlmtNursingWorkType other = (KnlmtNursingWorkType) object;
        if ((this.knlmtNursingWorkTypePK == null && other.knlmtNursingWorkTypePK != null)
                || (this.knlmtNursingWorkTypePK != null
                        && !this.knlmtNursingWorkTypePK.equals(other.knlmtNursingWorkTypePK))) {
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
        return this.knlmtNursingWorkTypePK;
    }
}
