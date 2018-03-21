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
@Table(name = "KRCST_EMP_DEFOR_M_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findAll", query = "SELECT k FROM KrcstEmpDeforMCalSet k"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByInsDate", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByInsCcd", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByInsScd", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByInsPg", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByUpdDate", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByUpdCcd", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByUpdScd", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByUpdPg", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByExclusVer", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByCid", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.krcstEmpDeforMCalSetPK.cid = :cid"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByEmpCd", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.krcstEmpDeforMCalSetPK.empCd = :empCd"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByIncludeLegalOt", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.includeLegalOt = :includeLegalOt"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByIncludeHolidayOt", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.includeHolidayOt = :includeHolidayOt"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByIncludeExtraOt", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.includeExtraOt = :includeExtraOt"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByIncludeLegalAggr", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.includeLegalAggr = :includeLegalAggr"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByIncludeHolidayAggr", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.includeHolidayAggr = :includeHolidayAggr"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByIncludeExtraAggr", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.includeExtraAggr = :includeExtraAggr"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByIsOtIrg", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.isOtIrg = :isOtIrg"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByPeriod", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.period = :period"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByRepeatAtr", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.repeatAtr = :repeatAtr"),
    @NamedQuery(name = "KrcstEmpDeforMCalSet.findByStrMonth", query = "SELECT k FROM KrcstEmpDeforMCalSet k WHERE k.strMonth = :strMonth")})
public class KrcstEmpDeforMCalSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrcstEmpDeforMCalSetPK krcstEmpDeforMCalSetPK;
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

    public KrcstEmpDeforMCalSet() {
    }

    public KrcstEmpDeforMCalSet(KrcstEmpDeforMCalSetPK krcstEmpDeforMCalSetPK) {
        this.krcstEmpDeforMCalSetPK = krcstEmpDeforMCalSetPK;
    }

    public KrcstEmpDeforMCalSet(KrcstEmpDeforMCalSetPK krcstEmpDeforMCalSetPK, int exclusVer, short includeLegalOt, short includeHolidayOt, short includeExtraOt, short includeLegalAggr, short includeHolidayAggr, short includeExtraAggr, short isOtIrg, short period, short repeatAtr, short strMonth) {
        this.krcstEmpDeforMCalSetPK = krcstEmpDeforMCalSetPK;
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

    public KrcstEmpDeforMCalSet(String cid, String empCd) {
        this.krcstEmpDeforMCalSetPK = new KrcstEmpDeforMCalSetPK(cid, empCd);
    }

    public KrcstEmpDeforMCalSetPK getKrcstEmpDeforMCalSetPK() {
        return krcstEmpDeforMCalSetPK;
    }

    public void setKrcstEmpDeforMCalSetPK(KrcstEmpDeforMCalSetPK krcstEmpDeforMCalSetPK) {
        this.krcstEmpDeforMCalSetPK = krcstEmpDeforMCalSetPK;
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
        hash += (krcstEmpDeforMCalSetPK != null ? krcstEmpDeforMCalSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstEmpDeforMCalSet)) {
            return false;
        }
        KrcstEmpDeforMCalSet other = (KrcstEmpDeforMCalSet) object;
        if ((this.krcstEmpDeforMCalSetPK == null && other.krcstEmpDeforMCalSetPK != null) || (this.krcstEmpDeforMCalSetPK != null && !this.krcstEmpDeforMCalSetPK.equals(other.krcstEmpDeforMCalSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstEmpDeforMCalSet[ krcstEmpDeforMCalSetPK=" + krcstEmpDeforMCalSetPK + " ]";
    }
    
}
