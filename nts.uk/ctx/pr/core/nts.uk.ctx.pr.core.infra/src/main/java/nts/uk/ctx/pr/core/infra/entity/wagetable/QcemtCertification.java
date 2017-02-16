/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * The Class QcemtCertification.
 */
@Data
@Entity
@Table(name = "qcemt_certification")
public class QcemtCertification implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The qcemt certification PK. */
    @EmbeddedId
    protected QcemtCertificationPK qcemtCertificationPK;
    
    /** The ins date. */
    @Column(name = "ins_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;
    
    /** The ins ccd. */
    @Column(name = "ins_ccd")
    private String insCcd;
    
    /** The ins scd. */
    @Column(name = "ins_scd")
    private String insScd;
    
    /** The ins pg. */
    @Column(name = "ins_pg")
    private String insPg;
    
    /** The upd date. */
    @Column(name = "upd_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    
    /** The upd ccd. */
    @Column(name = "upd_ccd")
    private String updCcd;
    
    /** The upd scd. */
    @Column(name = "upd_scd")
    private String updScd;
    
    /** The upd pg. */
    @Column(name = "upd_pg")
    private String updPg;
    
    /** The exclus ver. */
    @Basic(optional = false)
    @Column(name = "exclus_ver")
    private long exclusVer;
    
    /** The name. */
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    /** The qwtmt wagetable certify list. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "qcemtCertification")
    private List<QwtmtWagetableCertify> qwtmtWagetableCertifyList;

    /**
     * Instantiates a new qcemt certification.
     */
    public QcemtCertification() {
    }

    /**
     * Instantiates a new qcemt certification.
     *
     * @param qcemtCertificationPK the qcemt certification PK
     */
    public QcemtCertification(QcemtCertificationPK qcemtCertificationPK) {
        this.qcemtCertificationPK = qcemtCertificationPK;
    }

    /**
     * Instantiates a new qcemt certification.
     *
     * @param qcemtCertificationPK the qcemt certification PK
     * @param exclusVer the exclus ver
     * @param name the name
     */
    public QcemtCertification(QcemtCertificationPK qcemtCertificationPK, long exclusVer, String name) {
        this.qcemtCertificationPK = qcemtCertificationPK;
        this.exclusVer = exclusVer;
        this.name = name;
    }

    /**
     * Instantiates a new qcemt certification.
     *
     * @param ccd the ccd
     * @param certCd the cert cd
     */
    public QcemtCertification(String ccd, String certCd) {
        this.qcemtCertificationPK = new QcemtCertificationPK(ccd, certCd);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qcemtCertificationPK != null ? qcemtCertificationPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof QcemtCertification)) {
            return false;
        }
        QcemtCertification other = (QcemtCertification) object;
        if ((this.qcemtCertificationPK == null && other.qcemtCertificationPK != null) || (this.qcemtCertificationPK != null && !this.qcemtCertificationPK.equals(other.qcemtCertificationPK))) {
            return false;
        }
        return true;
    }
    
}
