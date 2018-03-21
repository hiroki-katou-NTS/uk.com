/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "KRCST_COM_FLEX_M_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KrcstComFlexMCalSet.findAll", query = "SELECT k FROM KrcstComFlexMCalSet k"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByInsDate", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByInsCcd", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByInsScd", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByInsPg", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByUpdDate", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByUpdCcd", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByUpdScd", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByUpdPg", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByExclusVer", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByCid", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.cid = :cid"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByInsufficSet", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.insufficSet = :insufficSet"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByIncludeOt", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.includeOt = :includeOt"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByAggrMethod", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.aggrMethod = :aggrMethod"),
    @NamedQuery(name = "KrcstComFlexMCalSet.findByLegalAggrSet", query = "SELECT k FROM KrcstComFlexMCalSet k WHERE k.legalAggrSet = :legalAggrSet")})
public class KrcstComFlexMCalSet implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
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

    public KrcstComFlexMCalSet() {
    }

    public KrcstComFlexMCalSet(String cid) {
        this.cid = cid;
    }

    public KrcstComFlexMCalSet(String cid, int exclusVer, short insufficSet, short includeOt, short aggrMethod, short legalAggrSet) {
        this.cid = cid;
        this.exclusVer = exclusVer;
        this.insufficSet = insufficSet;
        this.includeOt = includeOt;
        this.aggrMethod = aggrMethod;
        this.legalAggrSet = legalAggrSet;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstComFlexMCalSet)) {
            return false;
        }
        KrcstComFlexMCalSet other = (KrcstComFlexMCalSet) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstComFlexMCalSet[ cid=" + cid + " ]";
    }
    
}
