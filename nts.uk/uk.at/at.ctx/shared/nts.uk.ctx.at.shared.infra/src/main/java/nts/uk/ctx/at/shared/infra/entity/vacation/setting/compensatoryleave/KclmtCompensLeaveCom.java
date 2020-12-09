/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;
import java.util.List;

//import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KclmtCompensLeaveCom.
 */
@Setter
@Getter
@Entity
@Table(name = "KCLMT_COMPENS_LEAVE_COM")
public class KclmtCompensLeaveCom extends ContractUkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The cid. */
    @Id
    @Column(name = "CID")
    private String cid;

    /** The manage atr. */
    @Column(name = "MANAGE_ATR")
    private Integer manageAtr;
    
    /** The manage atr. */
    @Column(name = "DEADL_CHECK_MONTH")
    private Integer deadlCheckMonth;
    
    /** The Kclmt acquisition com. */
    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public KclmtAcquisitionCom KclmtAcquisitionCom;
    
    /** The Kctmt digest time com. */
    @OneToOne(optional = true, cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private KctmtDigestTimeCom KctmtDigestTimeCom;
    
    /** The list occurrence. */
    @JoinColumns(@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true))
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<KocmtOccurrenceSet> listOccurrence;

    /**
     * Instantiates a new kclmt compens leave com.
     */
    public KclmtCompensLeaveCom() {
    	super();
    }

    /**
     * Instantiates a new kclmt compens leave com.
     *
     * @param cid
     *            the cid
     */
    public KclmtCompensLeaveCom(String cid) {
        this.cid = cid;
    }

    /**
     * Instantiates a new kclmt compens leave com.
     *
     * @param cid
     *            the cid
     * @param manageAtr
     *            the manage atr
     */
    public KclmtCompensLeaveCom(String cid, Integer manageAtr) {
        this.cid = cid;
        this.manageAtr = manageAtr;
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
        // not set
        if (!(object instanceof KclmtCompensLeaveCom)) {
            return false;
        }
        KclmtCompensLeaveCom other = (KclmtCompensLeaveCom) object;
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
