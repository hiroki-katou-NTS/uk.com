/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KmfmtWorkType.
 */
@Setter
@Getter
@Entity
@Table(name = "KMFMT_NURSING_WORK_TYPE")
public class KmfmtNursingWorkType extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kmfmtWorkTypePK. */
    @EmbeddedId
    private KmfmtNursingWorkTypePK kmfmtWorkTypePK;
    
    /** The work type code. */
    @Size(max = 8)
    @Column(name = "DAY_CD")
    private String workTypeCode;

    /**
     * Instantiates a new kmfmt work type.
     */
    public KmfmtNursingWorkType() {
    }
    
    public KmfmtNursingWorkType(KmfmtNursingWorkTypePK kmfmtWorkTypePK, String workTypeCode) {
        this.kmfmtWorkTypePK = kmfmtWorkTypePK;
        this.workTypeCode = workTypeCode;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kmfmtWorkTypePK != null ? kmfmtWorkTypePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof KmfmtNursingWorkType)) {
            return false;
        }
        KmfmtNursingWorkType other = (KmfmtNursingWorkType) object;
        if ((this.kmfmtWorkTypePK == null && other.kmfmtWorkTypePK != null) || (this.kmfmtWorkTypePK != null
                && !this.kmfmtWorkTypePK.equals(other.kmfmtWorkTypePK))) {
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
        return this.kmfmtWorkTypePK;
    }
}
