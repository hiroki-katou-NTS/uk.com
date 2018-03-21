/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee;

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
@Table(name = "KRCST_SHA_REG_M_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KrcstShaRegMCalSet.findAll", query = "SELECT k FROM KrcstShaRegMCalSet k"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByInsDate", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByInsCcd", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByInsScd", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByInsPg", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByUpdDate", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByUpdCcd", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByUpdScd", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByUpdPg", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByExclusVer", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByCid", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.krcstShaRegMCalSetPK.cid = :cid"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findBySid", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.krcstShaRegMCalSetPK.sid = :sid"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByIncludeLegalOt", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.includeLegalOt = :includeLegalOt"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByIncludeHolidayOt", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.includeHolidayOt = :includeHolidayOt"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByIncludeExtraOt", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.includeExtraOt = :includeExtraOt"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByIncludeLegalAggr", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.includeLegalAggr = :includeLegalAggr"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByIncludeHolidayAggr", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.includeHolidayAggr = :includeHolidayAggr"),
    @NamedQuery(name = "KrcstShaRegMCalSet.findByIncludeExtraAggr", query = "SELECT k FROM KrcstShaRegMCalSet k WHERE k.includeExtraAggr = :includeExtraAggr")})
public class KrcstShaRegMCalSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrcstShaRegMCalSetPK krcstShaRegMCalSetPK;
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

    public KrcstShaRegMCalSet() {
    }

    public KrcstShaRegMCalSet(KrcstShaRegMCalSetPK krcstShaRegMCalSetPK) {
        this.krcstShaRegMCalSetPK = krcstShaRegMCalSetPK;
    }

    public KrcstShaRegMCalSet(KrcstShaRegMCalSetPK krcstShaRegMCalSetPK, int exclusVer, short includeLegalOt, short includeHolidayOt, short includeExtraOt, short includeLegalAggr, short includeHolidayAggr, short includeExtraAggr) {
        this.krcstShaRegMCalSetPK = krcstShaRegMCalSetPK;
        this.exclusVer = exclusVer;
        this.includeLegalOt = includeLegalOt;
        this.includeHolidayOt = includeHolidayOt;
        this.includeExtraOt = includeExtraOt;
        this.includeLegalAggr = includeLegalAggr;
        this.includeHolidayAggr = includeHolidayAggr;
        this.includeExtraAggr = includeExtraAggr;
    }

    public KrcstShaRegMCalSet(String cid, String sid) {
        this.krcstShaRegMCalSetPK = new KrcstShaRegMCalSetPK(cid, sid);
    }

    public KrcstShaRegMCalSetPK getKrcstShaRegMCalSetPK() {
        return krcstShaRegMCalSetPK;
    }

    public void setKrcstShaRegMCalSetPK(KrcstShaRegMCalSetPK krcstShaRegMCalSetPK) {
        this.krcstShaRegMCalSetPK = krcstShaRegMCalSetPK;
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
        hash += (krcstShaRegMCalSetPK != null ? krcstShaRegMCalSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstShaRegMCalSet)) {
            return false;
        }
        KrcstShaRegMCalSet other = (KrcstShaRegMCalSet) object;
        if ((this.krcstShaRegMCalSetPK == null && other.krcstShaRegMCalSetPK != null) || (this.krcstShaRegMCalSetPK != null && !this.krcstShaRegMCalSetPK.equals(other.krcstShaRegMCalSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstShaRegMCalSet[ krcstShaRegMCalSetPK=" + krcstShaRegMCalSetPK + " ]";
    }
    
}
