/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KtvmtTimeVacationSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KTVMT_TIME_ANNUAL_SET")
public class KshmtHdpaidTimeSet extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The cid. */
    @Id
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;
    
    /** The time manage atr. */
    @Column(name = "TIME_MANAGE_ATR")
    private Integer timeManageAtr;
    
    /** The time unit. */
    @Column(name = "TIME_UNIT")
    private Integer timeUnit;
    
    /** The time max day manage atr. */
    @Column(name = "TIME_MAX_DAY_MANAGE_ATR")
    private Integer timeMaxDayManageAtr;
    
    /** The time max day reference. */
    @Column(name = "TIME_MAX_DAY_REFERENCE")
    private Integer timeMaxDayReference;
    
    /** The time max day unif comp. */
    @Column(name = "TIME_MAX_DAY_UNIF_COMP")
    private Integer timeMaxDayUnifComp;
    
    /** The is enough time one day. */
    @Column(name = "IS_ENOUGH_TIME_ONE_DAY")
    private Integer isEnoughTimeOneDay;
    
    /** The Round Processing Classification. */
    @Column(name = "ROUND_PRO_CLA")
    private Integer roundProcessCla;

    /**
     * Instantiates a new ktvmt time vacation set.
     */
    public KshmtHdpaidTimeSet() {
    }

    /**
     * Instantiates a new ktvmt time vacation set.
     *
     * @param cid the cid
     */
    public KshmtHdpaidTimeSet(String cid) {
        this.cid = cid;
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
        if (!(object instanceof KshmtHdpaidTimeSet)) {
            return false;
        }
        KshmtHdpaidTimeSet other = (KshmtHdpaidTimeSet) object;
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
