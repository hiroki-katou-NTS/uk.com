/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.bs.employee.infra.entity.classification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Getter
@Setter
@Entity
@Table(name = "BSYMT_CLASSIFICATION")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "BsymtClassification.findAll", query = "SELECT b FROM BsymtClassification b"),
//    @NamedQuery(name = "BsymtClassification.findByInsDate", query = "SELECT b FROM BsymtClassification b WHERE b.insDate = :insDate"),
//    @NamedQuery(name = "BsymtClassification.findByInsCcd", query = "SELECT b FROM BsymtClassification b WHERE b.insCcd = :insCcd"),
//    @NamedQuery(name = "BsymtClassification.findByInsScd", query = "SELECT b FROM BsymtClassification b WHERE b.insScd = :insScd"),
//    @NamedQuery(name = "BsymtClassification.findByInsPg", query = "SELECT b FROM BsymtClassification b WHERE b.insPg = :insPg"),
//    @NamedQuery(name = "BsymtClassification.findByUpdDate", query = "SELECT b FROM BsymtClassification b WHERE b.updDate = :updDate"),
//    @NamedQuery(name = "BsymtClassification.findByUpdCcd", query = "SELECT b FROM BsymtClassification b WHERE b.updCcd = :updCcd"),
//    @NamedQuery(name = "BsymtClassification.findByUpdScd", query = "SELECT b FROM BsymtClassification b WHERE b.updScd = :updScd"),
//    @NamedQuery(name = "BsymtClassification.findByUpdPg", query = "SELECT b FROM BsymtClassification b WHERE b.updPg = :updPg"),
//    @NamedQuery(name = "BsymtClassification.findByExclusVer", query = "SELECT b FROM BsymtClassification b WHERE b.exclusVer = :exclusVer"),
//    @NamedQuery(name = "BsymtClassification.findByCid", query = "SELECT b FROM BsymtClassification b WHERE b.bsymtClassificationPK.cid = :cid"),
//    @NamedQuery(name = "BsymtClassification.findByClscd", query = "SELECT b FROM BsymtClassification b WHERE b.bsymtClassificationPK.clscd = :clscd"),
//    @NamedQuery(name = "BsymtClassification.findByClsname", query = "SELECT b FROM BsymtClassification b WHERE b.clsname = :clsname"),
//    @NamedQuery(name = "BsymtClassification.findByMemo", query = "SELECT b FROM BsymtClassification b WHERE b.memo = :memo")
	})
public class BsymtClassification implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BsymtClassificationPK bsymtClassificationPK;
    @Column(name = "INS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;
    @Size(max = 4)
    @Column(name = "INS_CCD")
    private String insCcd;
    @Size(max = 12)
    @Column(name = "INS_SCD")
    private String insScd;
    @Size(max = 14)
    @Column(name = "INS_PG")
    private String insPg;
    @Column(name = "UPD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    @Size(max = 4)
    @Column(name = "UPD_CCD")
    private String updCcd;
    @Size(max = 12)
    @Column(name = "UPD_SCD")
    private String updScd;
    @Size(max = 14)
    @Column(name = "UPD_PG")
    private String updPg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CLSNAME")
    private String clsname;
    @Size(max = 500)
    @Column(name = "MEMO")
    private String memo;

    public BsymtClassification() {
    }

    public BsymtClassification(BsymtClassificationPK bsymtClassificationPK) {
        this.bsymtClassificationPK = bsymtClassificationPK;
    }

    public BsymtClassification(BsymtClassificationPK bsymtClassificationPK, int exclusVer, String clsname) {
        this.bsymtClassificationPK = bsymtClassificationPK;
        this.exclusVer = exclusVer;
        this.clsname = clsname;
    }

    public BsymtClassification(String cid, String clscd) {
        this.bsymtClassificationPK = new BsymtClassificationPK(cid, clscd);
    }

    public BsymtClassificationPK getBsymtClassificationPK() {
        return bsymtClassificationPK;
    }

    public void setBsymtClassificationPK(BsymtClassificationPK bsymtClassificationPK) {
        this.bsymtClassificationPK = bsymtClassificationPK;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bsymtClassificationPK != null ? bsymtClassificationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BsymtClassification)) {
            return false;
        }
        BsymtClassification other = (BsymtClassification) object;
        if ((this.bsymtClassificationPK == null && other.bsymtClassificationPK != null) || (this.bsymtClassificationPK != null && !this.bsymtClassificationPK.equals(other.bsymtClassificationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BsymtClassification[ bsymtClassificationPK=" + bsymtClassificationPK + " ]";
    }
    
}
