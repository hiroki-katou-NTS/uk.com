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
@Table(name = "KRCST_COM_DEFOR_M_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KrcstComDeforMCalSet.findAll", query = "SELECT k FROM KrcstComDeforMCalSet k"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByInsDate", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByInsCcd", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByInsScd", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByInsPg", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByUpdDate", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByUpdCcd", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByUpdScd", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByUpdPg", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByExclusVer", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByCid", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.cid = :cid"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByIncludeLegalOt", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.includeLegalOt = :includeLegalOt"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByIncludeHolidayOt", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.includeHolidayOt = :includeHolidayOt"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByIncludeExtraOt", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.includeExtraOt = :includeExtraOt"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByIncludeLegalAggr", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.includeLegalAggr = :includeLegalAggr"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByIncludeHolidayAggr", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.includeHolidayAggr = :includeHolidayAggr"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByIncludeExtraAggr", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.includeExtraAggr = :includeExtraAggr"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByIsOtIrg", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.isOtIrg = :isOtIrg"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByPeriod", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.period = :period"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByRepeatAtr", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.repeatAtr = :repeatAtr"),
    @NamedQuery(name = "KrcstComDeforMCalSet.findByStrMonth", query = "SELECT k FROM KrcstComDeforMCalSet k WHERE k.strMonth = :strMonth")})
public class KrcstComDeforMCalSet implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_OT_IRG")
    private short isOtIrg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERIOD")
    private short period;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPEAT_ATR")
    private short repeatAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_MONTH")
    private short strMonth;

    public KrcstComDeforMCalSet() {
    }

    public KrcstComDeforMCalSet(String cid) {
        this.cid = cid;
    }

    public KrcstComDeforMCalSet(String cid, int exclusVer, short includeLegalOt, short includeHolidayOt, short includeExtraOt, short includeLegalAggr, short includeHolidayAggr, short includeExtraAggr, short isOtIrg, short period, short repeatAtr, short strMonth) {
        this.cid = cid;
        this.exclusVer = exclusVer;
        this.includeLegalOt = includeLegalOt;
        this.includeHolidayOt = includeHolidayOt;
        this.includeExtraOt = includeExtraOt;
        this.includeLegalAggr = includeLegalAggr;
        this.includeHolidayAggr = includeHolidayAggr;
        this.includeExtraAggr = includeExtraAggr;
        this.isOtIrg = isOtIrg;
        this.period = period;
        this.repeatAtr = repeatAtr;
        this.strMonth = strMonth;
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

    public short getIsOtIrg() {
        return isOtIrg;
    }

    public void setIsOtIrg(short isOtIrg) {
        this.isOtIrg = isOtIrg;
    }

    public short getPeriod() {
        return period;
    }

    public void setPeriod(short period) {
        this.period = period;
    }

    public short getRepeatAtr() {
        return repeatAtr;
    }

    public void setRepeatAtr(short repeatAtr) {
        this.repeatAtr = repeatAtr;
    }

    public short getStrMonth() {
        return strMonth;
    }

    public void setStrMonth(short strMonth) {
        this.strMonth = strMonth;
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
        if (!(object instanceof KrcstComDeforMCalSet)) {
            return false;
        }
        KrcstComDeforMCalSet other = (KrcstComDeforMCalSet) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstComDeforMCalSet[ cid=" + cid + " ]";
    }
    
}
