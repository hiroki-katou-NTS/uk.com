/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * Sets the list work type.
 *
 * @param listWorkType the new list work type
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_HDNURSING_LEAVE")
public class KshmtHdnursingLeave extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmtHdnursingLeavePK. */
    @EmbeddedId
    private KshmtHdnursingLeavePK kshmtHdnursingLeavePK;
    
    /** The manage type. */
    @Column(name = "MANAGE_ATR")
    private Integer manageType;
    
    /** The start md. */
    @Column(name = "STR_MD")
    private Integer startMonthDay;
    
    /** The nursing num leave day. */
    @Column(name = "NUM_LEAVE_DAY")
    private Integer nursingNumLeaveDay;
    
    /** The nursing num person. */
    @Column(name = "NUM_PERSON")
    private Integer nursingNumPerson;
    
    /** The special holiday frame. */
    @Column(name = "SPE_HOLIDAY")
    private Integer specialHolidayFrame;
    
    /** The work absence. */
    @Column(name = "WORK_ABS")
    private Integer workAbsence;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kshmtHdnursingLeave", orphanRemoval = true)
    private List<KnlmtNursingWorkType> listWorkType;

    /**
     * Instantiates a new kmfmt nursing leave set.
     */
    public KshmtHdnursingLeave() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtHdnursingLeavePK != null ? kshmtHdnursingLeavePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHdnursingLeave)) {
            return false;
        }
        KshmtHdnursingLeave other = (KshmtHdnursingLeave) object;
        if ((this.kshmtHdnursingLeavePK == null && other.kshmtHdnursingLeavePK != null)
                || (this.kshmtHdnursingLeavePK != null && !this.kshmtHdnursingLeavePK.equals(
                        other.kshmtHdnursingLeavePK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.kshmtHdnursingLeavePK;
    }
}
