/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KmfmtNursingLeaveSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KMFMT_NURSING_LEAVE_SET")
public class KmfmtNursingLeaveSet extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kmfmtNursingLeaveSetPK. */
    @EmbeddedId
    private KmfmtNursingLeaveSetPK kmfmtNursingLeaveSetPK;
    
    /** The manage type. */
    @Basic(optional = false)
    @Column(name = "MANAGE_TYPE")
    private Integer manageType;
    
    /** The start md. */
    @Column(name = "START_MD")
    private Integer startMonthDay;
    
    /** The nursing num leave day. */
    @Column(name = "NURSING_NUM_LEAVE_DAY")
    private Integer nursingNumLeaveDay;
    
    /** The nursing num person. */
    @Column(name = "NURSING_NUM_PERSON")
    private Integer nursingNumPerson;
    
    /** The list work type. */
    @JoinColumns({@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = false),
            @JoinColumn(name = "NURSING_CTR", referencedColumnName = "NURSING_CTR", insertable = true, updatable = false)})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<KmfmtWorkType> listWorkType;

    /**
     * Instantiates a new kmfmt nursing leave set.
     */
    public KmfmtNursingLeaveSet() {
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kmfmtNursingLeaveSetPK != null ? kmfmtNursingLeaveSetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KmfmtNursingLeaveSet)) {
            return false;
        }
        KmfmtNursingLeaveSet other = (KmfmtNursingLeaveSet) object;
        if ((this.kmfmtNursingLeaveSetPK == null && other.kmfmtNursingLeaveSetPK != null)
                || (this.kmfmtNursingLeaveSetPK != null && !this.kmfmtNursingLeaveSetPK.equals(
                        other.kmfmtNursingLeaveSetPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.kmfmtNursingLeaveSetPK;
    }
}
