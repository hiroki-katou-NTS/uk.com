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
@Table(name = "KRCST_EMP_REG_M_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KrcstEmpRegMCalSet.findAll", query = "SELECT k FROM KrcstEmpRegMCalSet k"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByInsDate", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByInsCcd", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByInsScd", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByInsPg", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByUpdDate", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByUpdCcd", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByUpdScd", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByUpdPg", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByExclusVer", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByCid", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.krcstEmpRegMCalSetPK.cid = :cid"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByEmpCd", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.krcstEmpRegMCalSetPK.empCd = :empCd"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByIncludeLegalOt", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.includeLegalOt = :includeLegalOt"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByIncludeHolidayOt", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.includeHolidayOt = :includeHolidayOt"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByIncludeExtraOt", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.includeExtraOt = :includeExtraOt"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByIncludeLegalAggr", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.includeLegalAggr = :includeLegalAggr"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByIncludeHolidayAggr", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.includeHolidayAggr = :includeHolidayAggr"),
    @NamedQuery(name = "KrcstEmpRegMCalSet.findByIncludeExtraAggr", query = "SELECT k FROM KrcstEmpRegMCalSet k WHERE k.includeExtraAggr = :includeExtraAggr")})
public class KrcstEmpRegMCalSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrcstEmpRegMCalSetPK krcstEmpRegMCalSetPK;
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

    public KrcstEmpRegMCalSet() {
    }

    public KrcstEmpRegMCalSet(KrcstEmpRegMCalSetPK krcstEmpRegMCalSetPK) {
        this.krcstEmpRegMCalSetPK = krcstEmpRegMCalSetPK;
    }

    public KrcstEmpRegMCalSet(KrcstEmpRegMCalSetPK krcstEmpRegMCalSetPK, int exclusVer, short includeLegalOt, short includeHolidayOt, short includeExtraOt, short includeLegalAggr, short includeHolidayAggr, short includeExtraAggr) {
        this.krcstEmpRegMCalSetPK = krcstEmpRegMCalSetPK;
        this.exclusVer = exclusVer;
        this.includeLegalOt = includeLegalOt;
        this.includeHolidayOt = includeHolidayOt;
        this.includeExtraOt = includeExtraOt;
        this.includeLegalAggr = includeLegalAggr;
        this.includeHolidayAggr = includeHolidayAggr;
        this.includeExtraAggr = includeExtraAggr;
    }

    public KrcstEmpRegMCalSet(String cid, String empCd) {
        this.krcstEmpRegMCalSetPK = new KrcstEmpRegMCalSetPK(cid, empCd);
    }

    public KrcstEmpRegMCalSetPK getKrcstEmpRegMCalSetPK() {
        return krcstEmpRegMCalSetPK;
    }

    public void setKrcstEmpRegMCalSetPK(KrcstEmpRegMCalSetPK krcstEmpRegMCalSetPK) {
        this.krcstEmpRegMCalSetPK = krcstEmpRegMCalSetPK;
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
        hash += (krcstEmpRegMCalSetPK != null ? krcstEmpRegMCalSetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstEmpRegMCalSet)) {
            return false;
        }
        KrcstEmpRegMCalSet other = (KrcstEmpRegMCalSet) object;
        if ((this.krcstEmpRegMCalSetPK == null && other.krcstEmpRegMCalSetPK != null) || (this.krcstEmpRegMCalSetPK != null && !this.krcstEmpRegMCalSetPK.equals(other.krcstEmpRegMCalSetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstEmpRegMCalSet[ krcstEmpRegMCalSetPK=" + krcstEmpRegMCalSetPK + " ]";
    }
    
}
