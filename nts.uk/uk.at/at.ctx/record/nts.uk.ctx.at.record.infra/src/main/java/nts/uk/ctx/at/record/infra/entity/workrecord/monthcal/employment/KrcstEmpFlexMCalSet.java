/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment;

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
@Table(name = "KRCST_EMP_FLEX_M_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findAll", query = "SELECT k FROM KrcstEmpFlexMCalSet k"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByInsDate", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByInsCcd", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByInsScd", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByInsPg", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByUpdDate", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByUpdCcd", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByUpdScd", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByUpdPg", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByExclusVer", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByCid", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.krcstEmpFlexMCalSetPK.cid = :cid"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByEmpCd", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.krcstEmpFlexMCalSetPK.empCd = :empCd"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByInsufficSet", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.insufficSet = :insufficSet"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByIncludeOt", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.includeOt = :includeOt"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByAggrMethod", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.aggrMethod = :aggrMethod"),
    @NamedQuery(name = "KrcstEmpFlexMCalSet.findByLegalAggrSet", query = "SELECT k FROM KrcstEmpFlexMCalSet k WHERE k.legalAggrSet = :legalAggrSet")})
public class KrcstEmpFlexMCalSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrcstEmpFlexMCalSetPK krcstEmpFlexMCalSetPK;
    @Column(name = "INS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;
    @Size(max = 4)
    @Column(name = "INS_CCD")
    private String insCcd;
    @Size(max = 30)
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
    @Size(max = 30)
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
    @Column(name = "INSUFFIC_SET")
    private short insufficSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INCLUDE_OT")
    private short includeOt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AGGR_METHOD")
    private short aggrMethod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_AGGR_SET")
    private short legalAggrSet;

    public KrcstEmpFlexMCalSet() {
    }

    public KrcstEmpFlexMCalSet(KrcstEmpFlexMCalSetPK krcstEmpFlexMCalSetPK) {
        this.krcstEmpFlexMCalSetPK = krcstEmpFlexMCalSetPK;
    }

    public KrcstEmpFlexMCalSet(KrcstEmpFlexMCalSetPK krcstEmpFlexMCalSetPK, int exclusVer, short insufficSet, short includeOt, short aggrMethod, short legalAggrSet) {
        this.krcstEmpFlexMCalSetPK = krcstEmpFlexMCalSetPK;
        this.exclusVer = exclusVer;
        this.insufficSet = insufficSet;
        this.includeOt = includeOt;
        this.aggrMethod = aggrMethod;
        this.legalAggrSet = legalAggrSet;
    }

    public KrcstEmpFlexMCalSet(String cid, String empCd) {
        this.krcstEmpFlexMCalSetPK = new KrcstEmpFlexMCalSetPK(cid, empCd);
    }

    public KrcstEmpFlexMCalSetPK getKrcstEmpFlexMCalSetPK() {
        return krcstEmpFlexMCalSetPK;
    }

    public void setKrcstEmpFlexMCalSetPK(KrcstEmpFlexMCalSetPK krcstEmpFlexMCalSetPK) {
        this.krcstEmpFlexMCalSetPK = krcstEmpFlexMCalSetPK;
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

    public short getInsufficSet() {
        return insufficSet;
    }

    public void setInsufficSet(short insufficSet) {
        this.insufficSet = insufficSet;
    }

    public short getIncludeOt() {
        return includeOt;
    }

    public void setIncludeOt(short includeOt) {
        this.includeOt = includeOt;
    }

    public short getAggrMethod() {
        return aggrMethod;
    }

    public void setAggrMethod(short aggrMethod) {
        this.aggrMethod = aggrMethod;
    }

    public short getLegalAggrSet() {
        return legalAggrSet;
    }

    public void setLegalAggrSet(short legalAggrSet) {
        this.legalAggrSet = legalAggrSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (krcstEmpFlexMCalSetPK != null ? krcstEmpFlexMCalSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstEmpFlexMCalSet)) {
            return false;
        }
        KrcstEmpFlexMCalSet other = (KrcstEmpFlexMCalSet) object;
        if ((this.krcstEmpFlexMCalSetPK == null && other.krcstEmpFlexMCalSetPK != null) || (this.krcstEmpFlexMCalSetPK != null && !this.krcstEmpFlexMCalSetPK.equals(other.krcstEmpFlexMCalSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstEmpFlexMCalSet[ krcstEmpFlexMCalSetPK=" + krcstEmpFlexMCalSetPK + " ]";
    }
    
}
