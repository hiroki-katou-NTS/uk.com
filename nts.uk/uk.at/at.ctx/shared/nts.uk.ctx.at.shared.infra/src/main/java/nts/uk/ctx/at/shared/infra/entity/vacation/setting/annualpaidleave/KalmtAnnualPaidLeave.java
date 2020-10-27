/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KalmtAnnualPaidLeave.
 */
@Setter
@Getter
@Entity
@Table(name = "KALMT_ANNUAL_PAID_LEAVE")
public class KalmtAnnualPaidLeave extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The cid. */
    @Id
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;
    
    /** The priority type. */
    @Column(name = "PRIORITY_TYPE")
    private Integer priorityType;
    
    /** The manage atr. */
    @Basic(optional = false)
    @Column(name = "MANAGE_ATR")
    private Integer manageAtr;
    
    /** The kmamt mng annual set. */
    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public KmamtMngAnnualSet kmamtMngAnnualSet;
    
    /** The ktvmt time vacation set. */
    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public KtvmtTimeAnnualSet ktvmtTimeVacationSet;

    /**
     * Instantiates a new kalmt annual paid leave.
     */
    public KalmtAnnualPaidLeave() {
    }

    /**
     * Instantiates a new kalmt annual paid leave.
     *
     * @param cid the cid
     */
    public KalmtAnnualPaidLeave(String cid) {
        this.cid = cid;
    }

    /**
     * Instantiates a new kalmt annual paid leave.
     *
     * @param cid the cid
     * @param manageAtr the manage atr
     */
    public KalmtAnnualPaidLeave(String cid, Integer manageAtr) {
        this.cid = cid;
        this.manageAtr = manageAtr;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KalmtAnnualPaidLeave)) {
            return false;
        }
        KalmtAnnualPaidLeave other = (KalmtAnnualPaidLeave) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.cid;
    }
}
