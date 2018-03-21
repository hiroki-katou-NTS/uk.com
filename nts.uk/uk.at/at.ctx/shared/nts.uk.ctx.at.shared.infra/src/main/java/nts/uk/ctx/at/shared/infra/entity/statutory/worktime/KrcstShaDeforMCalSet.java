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
@Table(name = "KRCST_SHA_DEFOR_M_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KrcstShaDeforMCalSet.findAll", query = "SELECT k FROM KrcstShaDeforMCalSet k"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByInsDate", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByInsCcd", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByInsScd", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByInsPg", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByUpdDate", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByUpdCcd", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByUpdScd", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByUpdPg", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByExclusVer", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByCid", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.krcstShaDeforMCalSetPK.cid = :cid"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findBySid", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.krcstShaDeforMCalSetPK.sid = :sid"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByIncludeLegalOt", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.includeLegalOt = :includeLegalOt"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByIncludeHolidayOt", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.includeHolidayOt = :includeHolidayOt"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByIncludeExtraOt", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.includeExtraOt = :includeExtraOt"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByIncludeLegalAggr", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.includeLegalAggr = :includeLegalAggr"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByIncludeHolidayAggr", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.includeHolidayAggr = :includeHolidayAggr"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByIncludeExtraAggr", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.includeExtraAggr = :includeExtraAggr"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByIsOtIrg", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.isOtIrg = :isOtIrg"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByPeriod", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.period = :period"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByRepeatAtr", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.repeatAtr = :repeatAtr"),
    @NamedQuery(name = "KrcstShaDeforMCalSet.findByStrMonth", query = "SELECT k FROM KrcstShaDeforMCalSet k WHERE k.strMonth = :strMonth")})
public class KrcstShaDeforMCalSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrcstShaDeforMCalSetPK krcstShaDeforMCalSetPK;
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

    public KrcstShaDeforMCalSet() {
    }

    public KrcstShaDeforMCalSet(KrcstShaDeforMCalSetPK krcstShaDeforMCalSetPK) {
        this.krcstShaDeforMCalSetPK = krcstShaDeforMCalSetPK;
    }

    public KrcstShaDeforMCalSet(KrcstShaDeforMCalSetPK krcstShaDeforMCalSetPK, int exclusVer, short includeLegalOt, short includeHolidayOt, short includeExtraOt, short includeLegalAggr, short includeHolidayAggr, short includeExtraAggr, short isOtIrg, short period, short repeatAtr, short strMonth) {
        this.krcstShaDeforMCalSetPK = krcstShaDeforMCalSetPK;
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

    public KrcstShaDeforMCalSet(String cid, String sid) {
        this.krcstShaDeforMCalSetPK = new KrcstShaDeforMCalSetPK(cid, sid);
    }

    public KrcstShaDeforMCalSetPK getKrcstShaDeforMCalSetPK() {
        return krcstShaDeforMCalSetPK;
    }

    public void setKrcstShaDeforMCalSetPK(KrcstShaDeforMCalSetPK krcstShaDeforMCalSetPK) {
        this.krcstShaDeforMCalSetPK = krcstShaDeforMCalSetPK;
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
        hash += (krcstShaDeforMCalSetPK != null ? krcstShaDeforMCalSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstShaDeforMCalSet)) {
            return false;
        }
        KrcstShaDeforMCalSet other = (KrcstShaDeforMCalSet) object;
        if ((this.krcstShaDeforMCalSetPK == null && other.krcstShaDeforMCalSetPK != null) || (this.krcstShaDeforMCalSetPK != null && !this.krcstShaDeforMCalSetPK.equals(other.krcstShaDeforMCalSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstShaDeforMCalSet[ krcstShaDeforMCalSetPK=" + krcstShaDeforMCalSetPK + " ]";
    }
    
}
