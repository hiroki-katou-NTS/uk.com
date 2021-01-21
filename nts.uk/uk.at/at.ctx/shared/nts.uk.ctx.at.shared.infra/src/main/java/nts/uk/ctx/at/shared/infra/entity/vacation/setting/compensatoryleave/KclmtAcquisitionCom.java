/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

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
 * The Class KclmtAcquisitionCom.
 */
@Setter
@Getter
@Entity
@Table(name = "KCLMT_ACQUISITION_COM")
public class KclmtAcquisitionCom extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The cid. */
    @Id
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;

    /** The exp time. */
    @Basic(optional = false)
    @Column(name = "EXP_TIME")
    private Integer expTime;

    /** The preemp permit atr. */
    @Basic(optional = false)
    @Column(name = "PREEMP_PERMIT_ATR")
    private Integer preempPermitAtr;
    
    @Basic(optional = false)
    @Column(name = "DEADL_CHECK_MONTH")
    private Integer deadlCheckMonth;

    /**
     * Instantiates a new kclmt acquisition com.
     */
    public KclmtAcquisitionCom() {
    }

    /**
     * Instantiates a new kclmt acquisition com.
     *
     * @param cid
     *            the cid
     */
    public KclmtAcquisitionCom(String cid) {
        this.cid = cid;
    }

    /**
     * Instantiates a new kclmt acquisition com.
     *
     * @param cid
     *            the cid
     * @param expTime
     *            the exp time
     * @param preempPermitAtr
     *            the preemp permit atr
     */
    public KclmtAcquisitionCom(String cid, Integer expTime, Integer preempPermitAtr) {
        this.cid = cid;
        this.expTime = expTime;
        this.preempPermitAtr = preempPermitAtr;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KclmtAcquisitionCom)) {
            return false;
        }
        KclmtAcquisitionCom other = (KclmtAcquisitionCom) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
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
        return this.cid;
    }

}
