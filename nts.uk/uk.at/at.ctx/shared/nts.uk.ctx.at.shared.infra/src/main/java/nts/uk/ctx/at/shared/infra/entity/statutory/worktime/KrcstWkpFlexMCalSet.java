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
@Table(name = "KRCST_WKP_FLEX_M_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findAll", query = "SELECT k FROM KrcstWkpFlexMCalSet k"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByInsDate", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByInsCcd", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByInsScd", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByInsPg", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByUpdDate", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByUpdCcd", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByUpdScd", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByUpdPg", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByExclusVer", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByCid", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.krcstWkpFlexMCalSetPK.cid = :cid"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByWkpId", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.krcstWkpFlexMCalSetPK.wkpId = :wkpId"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByInsufficSet", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.insufficSet = :insufficSet"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByIncludeOt", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.includeOt = :includeOt"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByAggrMethod", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.aggrMethod = :aggrMethod"),
    @NamedQuery(name = "KrcstWkpFlexMCalSet.findByLegalAggrSet", query = "SELECT k FROM KrcstWkpFlexMCalSet k WHERE k.legalAggrSet = :legalAggrSet")})
public class KrcstWkpFlexMCalSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrcstWkpFlexMCalSetPK krcstWkpFlexMCalSetPK;
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

    public KrcstWkpFlexMCalSet() {
    }

    public KrcstWkpFlexMCalSet(KrcstWkpFlexMCalSetPK krcstWkpFlexMCalSetPK) {
        this.krcstWkpFlexMCalSetPK = krcstWkpFlexMCalSetPK;
    }

    public KrcstWkpFlexMCalSet(KrcstWkpFlexMCalSetPK krcstWkpFlexMCalSetPK, int exclusVer, short insufficSet, short includeOt, short aggrMethod, short legalAggrSet) {
        this.krcstWkpFlexMCalSetPK = krcstWkpFlexMCalSetPK;
        this.exclusVer = exclusVer;
        this.insufficSet = insufficSet;
        this.includeOt = includeOt;
        this.aggrMethod = aggrMethod;
        this.legalAggrSet = legalAggrSet;
    }

    public KrcstWkpFlexMCalSet(String cid, String wkpId) {
        this.krcstWkpFlexMCalSetPK = new KrcstWkpFlexMCalSetPK(cid, wkpId);
    }

    public KrcstWkpFlexMCalSetPK getKrcstWkpFlexMCalSetPK() {
        return krcstWkpFlexMCalSetPK;
    }

    public void setKrcstWkpFlexMCalSetPK(KrcstWkpFlexMCalSetPK krcstWkpFlexMCalSetPK) {
        this.krcstWkpFlexMCalSetPK = krcstWkpFlexMCalSetPK;
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
        hash += (krcstWkpFlexMCalSetPK != null ? krcstWkpFlexMCalSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstWkpFlexMCalSet)) {
            return false;
        }
        KrcstWkpFlexMCalSet other = (KrcstWkpFlexMCalSet) object;
        if ((this.krcstWkpFlexMCalSetPK == null && other.krcstWkpFlexMCalSetPK != null) || (this.krcstWkpFlexMCalSetPK != null && !this.krcstWkpFlexMCalSetPK.equals(other.krcstWkpFlexMCalSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstWkpFlexMCalSet[ krcstWkpFlexMCalSetPK=" + krcstWkpFlexMCalSetPK + " ]";
    }
    
}
