/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace;

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
@Table(name = "KRCST_WKP_REG_M_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KrcstWkpRegMCalSet.findAll", query = "SELECT k FROM KrcstWkpRegMCalSet k"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByInsDate", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByInsCcd", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByInsScd", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByInsPg", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByUpdDate", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByUpdCcd", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByUpdScd", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByUpdPg", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByExclusVer", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByCid", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.krcstWkpRegMCalSetPK.cid = :cid"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByWkpid", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.krcstWkpRegMCalSetPK.wkpid = :wkpid"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByIncludeLegalOt", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.includeLegalOt = :includeLegalOt"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByIncludeHolidayOt", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.includeHolidayOt = :includeHolidayOt"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByIncludeExtraOt", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.includeExtraOt = :includeExtraOt"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByIncludeLegalAggr", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.includeLegalAggr = :includeLegalAggr"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByIncludeHolidayAggr", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.includeHolidayAggr = :includeHolidayAggr"),
    @NamedQuery(name = "KrcstWkpRegMCalSet.findByIncludeExtraAggr", query = "SELECT k FROM KrcstWkpRegMCalSet k WHERE k.includeExtraAggr = :includeExtraAggr")})
public class KrcstWkpRegMCalSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrcstWkpRegMCalSetPK krcstWkpRegMCalSetPK;
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
    @Column(name = "INCLUDE_LEGAL_OT")
    private short includeLegalOt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INCLUDE_HOLIDAY_OT")
    private short includeHolidayOt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INCLUDE_EXTRA_OT")
    private short includeExtraOt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INCLUDE_LEGAL_AGGR")
    private short includeLegalAggr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INCLUDE_HOLIDAY_AGGR")
    private short includeHolidayAggr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INCLUDE_EXTRA_AGGR")
    private short includeExtraAggr;

    public KrcstWkpRegMCalSet() {
    }

    public KrcstWkpRegMCalSet(KrcstWkpRegMCalSetPK krcstWkpRegMCalSetPK) {
        this.krcstWkpRegMCalSetPK = krcstWkpRegMCalSetPK;
    }

    public KrcstWkpRegMCalSet(KrcstWkpRegMCalSetPK krcstWkpRegMCalSetPK, int exclusVer, short includeLegalOt, short includeHolidayOt, short includeExtraOt, short includeLegalAggr, short includeHolidayAggr, short includeExtraAggr) {
        this.krcstWkpRegMCalSetPK = krcstWkpRegMCalSetPK;
        this.exclusVer = exclusVer;
        this.includeLegalOt = includeLegalOt;
        this.includeHolidayOt = includeHolidayOt;
        this.includeExtraOt = includeExtraOt;
        this.includeLegalAggr = includeLegalAggr;
        this.includeHolidayAggr = includeHolidayAggr;
        this.includeExtraAggr = includeExtraAggr;
    }

    public KrcstWkpRegMCalSet(String cid, String wkpid) {
        this.krcstWkpRegMCalSetPK = new KrcstWkpRegMCalSetPK(cid, wkpid);
    }

    public KrcstWkpRegMCalSetPK getKrcstWkpRegMCalSetPK() {
        return krcstWkpRegMCalSetPK;
    }

    public void setKrcstWkpRegMCalSetPK(KrcstWkpRegMCalSetPK krcstWkpRegMCalSetPK) {
        this.krcstWkpRegMCalSetPK = krcstWkpRegMCalSetPK;
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

    public short getIncludeLegalOt() {
        return includeLegalOt;
    }

    public void setIncludeLegalOt(short includeLegalOt) {
        this.includeLegalOt = includeLegalOt;
    }

    public short getIncludeHolidayOt() {
        return includeHolidayOt;
    }

    public void setIncludeHolidayOt(short includeHolidayOt) {
        this.includeHolidayOt = includeHolidayOt;
    }

    public short getIncludeExtraOt() {
        return includeExtraOt;
    }

    public void setIncludeExtraOt(short includeExtraOt) {
        this.includeExtraOt = includeExtraOt;
    }

    public short getIncludeLegalAggr() {
        return includeLegalAggr;
    }

    public void setIncludeLegalAggr(short includeLegalAggr) {
        this.includeLegalAggr = includeLegalAggr;
    }

    public short getIncludeHolidayAggr() {
        return includeHolidayAggr;
    }

    public void setIncludeHolidayAggr(short includeHolidayAggr) {
        this.includeHolidayAggr = includeHolidayAggr;
    }

    public short getIncludeExtraAggr() {
        return includeExtraAggr;
    }

    public void setIncludeExtraAggr(short includeExtraAggr) {
        this.includeExtraAggr = includeExtraAggr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (krcstWkpRegMCalSetPK != null ? krcstWkpRegMCalSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstWkpRegMCalSet)) {
            return false;
        }
        KrcstWkpRegMCalSet other = (KrcstWkpRegMCalSet) object;
        if ((this.krcstWkpRegMCalSetPK == null && other.krcstWkpRegMCalSetPK != null) || (this.krcstWkpRegMCalSetPK != null && !this.krcstWkpRegMCalSetPK.equals(other.krcstWkpRegMCalSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstWkpRegMCalSet[ krcstWkpRegMCalSetPK=" + krcstWkpRegMCalSetPK + " ]";
    }
    
}
