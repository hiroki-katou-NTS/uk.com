/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

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

/**
 *
 * @author NWS_THANHNC_PC
 */
@Entity
@Table(name = "KSCMT_EST_PRICE_PER_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KscmtEstPricePerSet.findAll", query = "SELECT k FROM KscmtEstPricePerSet k"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByInsDate", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByInsCcd", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByInsScd", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByInsPg", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByUpdDate", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByUpdCcd", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByUpdScd", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByUpdPg", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByExclusVer", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KscmtEstPricePerSet.findBySid", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.kscmtEstPricePerSetPK.sid = :sid"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByTargetYear", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.kscmtEstPricePerSetPK.targetYear = :targetYear"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByTargetCls", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.kscmtEstPricePerSetPK.targetCls = :targetCls"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByEstCondition1stMny", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.estCondition1stMny = :estCondition1stMny"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByEstCondition2ndMny", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.estCondition2ndMny = :estCondition2ndMny"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByEstCondition3rdMny", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.estCondition3rdMny = :estCondition3rdMny"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByEstCondition4thMny", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.estCondition4thMny = :estCondition4thMny"),
    @NamedQuery(name = "KscmtEstPricePerSet.findByEstCondition5thMny", query = "SELECT k FROM KscmtEstPricePerSet k WHERE k.estCondition5thMny = :estCondition5thMny")})
public class KscmtEstPricePerSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KscmtEstPricePerSetPK kscmtEstPricePerSetPK;
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
    @Column(name = "EST_CONDITION_1ST_MNY")
    private int estCondition1stMny;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_2ND_MNY")
    private int estCondition2ndMny;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_3RD_MNY")
    private int estCondition3rdMny;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_4TH_MNY")
    private int estCondition4thMny;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_5TH_MNY")
    private int estCondition5thMny;

    public KscmtEstPricePerSet() {
    }

    public KscmtEstPricePerSet(KscmtEstPricePerSetPK kscmtEstPricePerSetPK) {
        this.kscmtEstPricePerSetPK = kscmtEstPricePerSetPK;
    }

    public KscmtEstPricePerSet(KscmtEstPricePerSetPK kscmtEstPricePerSetPK, int exclusVer, int estCondition1stMny, int estCondition2ndMny, int estCondition3rdMny, int estCondition4thMny, int estCondition5thMny) {
        this.kscmtEstPricePerSetPK = kscmtEstPricePerSetPK;
        this.exclusVer = exclusVer;
        this.estCondition1stMny = estCondition1stMny;
        this.estCondition2ndMny = estCondition2ndMny;
        this.estCondition3rdMny = estCondition3rdMny;
        this.estCondition4thMny = estCondition4thMny;
        this.estCondition5thMny = estCondition5thMny;
    }

    public KscmtEstPricePerSet(String sid, short targetYear, short targetCls) {
        this.kscmtEstPricePerSetPK = new KscmtEstPricePerSetPK(sid, targetYear, targetCls);
    }

    public KscmtEstPricePerSetPK getKscmtEstPricePerSetPK() {
        return kscmtEstPricePerSetPK;
    }

    public void setKscmtEstPricePerSetPK(KscmtEstPricePerSetPK kscmtEstPricePerSetPK) {
        this.kscmtEstPricePerSetPK = kscmtEstPricePerSetPK;
    }

    public Date getInsDate() {
        return insDate;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public String getInsCcd() {
        return insCcd;
    }

    public void setInsCcd(String insCcd) {
        this.insCcd = insCcd;
    }

    public String getInsScd() {
        return insScd;
    }

    public void setInsScd(String insScd) {
        this.insScd = insScd;
    }

    public String getInsPg() {
        return insPg;
    }

    public void setInsPg(String insPg) {
        this.insPg = insPg;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public String getUpdCcd() {
        return updCcd;
    }

    public void setUpdCcd(String updCcd) {
        this.updCcd = updCcd;
    }

    public String getUpdScd() {
        return updScd;
    }

    public void setUpdScd(String updScd) {
        this.updScd = updScd;
    }

    public String getUpdPg() {
        return updPg;
    }

    public void setUpdPg(String updPg) {
        this.updPg = updPg;
    }

    public int getExclusVer() {
        return exclusVer;
    }

    public void setExclusVer(int exclusVer) {
        this.exclusVer = exclusVer;
    }

    public int getEstCondition1stMny() {
        return estCondition1stMny;
    }

    public void setEstCondition1stMny(int estCondition1stMny) {
        this.estCondition1stMny = estCondition1stMny;
    }

    public int getEstCondition2ndMny() {
        return estCondition2ndMny;
    }

    public void setEstCondition2ndMny(int estCondition2ndMny) {
        this.estCondition2ndMny = estCondition2ndMny;
    }

    public int getEstCondition3rdMny() {
        return estCondition3rdMny;
    }

    public void setEstCondition3rdMny(int estCondition3rdMny) {
        this.estCondition3rdMny = estCondition3rdMny;
    }

    public int getEstCondition4thMny() {
        return estCondition4thMny;
    }

    public void setEstCondition4thMny(int estCondition4thMny) {
        this.estCondition4thMny = estCondition4thMny;
    }

    public int getEstCondition5thMny() {
        return estCondition5thMny;
    }

    public void setEstCondition5thMny(int estCondition5thMny) {
        this.estCondition5thMny = estCondition5thMny;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtEstPricePerSetPK != null ? kscmtEstPricePerSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscmtEstPricePerSet)) {
            return false;
        }
        KscmtEstPricePerSet other = (KscmtEstPricePerSet) object;
        if ((this.kscmtEstPricePerSetPK == null && other.kscmtEstPricePerSetPK != null) || (this.kscmtEstPricePerSetPK != null && !this.kscmtEstPricePerSetPK.equals(other.kscmtEstPricePerSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KscmtEstPricePerSet[ kscmtEstPricePerSetPK=" + kscmtEstPricePerSetPK + " ]";
    }
    
}
