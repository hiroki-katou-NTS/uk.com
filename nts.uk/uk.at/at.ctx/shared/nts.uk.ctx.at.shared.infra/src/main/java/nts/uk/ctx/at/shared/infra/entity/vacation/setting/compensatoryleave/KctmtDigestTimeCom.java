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
 * The Class KctmtDigestTimeCom.
 */
@Setter
@Getter
@Entity
@Table(name = "KCTMT_DIGEST_TIME_COM")
public class KctmtDigestTimeCom extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The cid. */
    @Id
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;

    /** The manage atr. */
    @Basic(optional = false)
    @Column(name = "MANAGE_ATR")
    private Integer manageAtr;

    /** The digestive unit. */
    @Basic(optional = false)
    @Column(name = "DIGESTIVE_UNIT")
    private Integer digestiveUnit;

    /**
     * Instantiates a new kctmt digest time com.
     */
    public KctmtDigestTimeCom() {
    }

    /**
     * Instantiates a new kctmt digest time com.
     *
     * @param cid
     *            the cid
     */
    public KctmtDigestTimeCom(String cid) {
        this.cid = cid;
    }

    /**
     * Instantiates a new kctmt digest time com.
     *
     * @param cid
     *            the cid
     * @param manageAtr
     *            the manage atr
     * @param digestiveUnit
     *            the digestive unit
     */
    public KctmtDigestTimeCom(String cid, Integer manageAtr, Integer digestiveUnit) {
        this.cid = cid;
        this.manageAtr = manageAtr;
        this.digestiveUnit = digestiveUnit;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
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
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KctmtDigestTimeCom)) {
            return false;
        }
        KctmtDigestTimeCom other = (KctmtDigestTimeCom) object;
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
