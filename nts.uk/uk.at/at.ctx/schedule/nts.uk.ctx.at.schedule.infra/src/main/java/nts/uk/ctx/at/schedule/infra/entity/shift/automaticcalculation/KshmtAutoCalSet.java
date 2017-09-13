package nts.uk.ctx.at.schedule.infra.entity.shift.automaticcalculation;

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
@Table(name = "KSHMT_AUTO_CAL_SET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshmtAutoCalSet.findAll", query = "SELECT k FROM KshmtAutoCalSet k"),
    @NamedQuery(name = "KshmtAutoCalSet.findByInsDate", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshmtAutoCalSet.findByInsCcd", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshmtAutoCalSet.findByInsScd", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshmtAutoCalSet.findByInsPg", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshmtAutoCalSet.findByUpdDate", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshmtAutoCalSet.findByUpdCcd", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshmtAutoCalSet.findByUpdScd", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshmtAutoCalSet.findByUpdPg", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshmtAutoCalSet.findByExclusVer", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshmtAutoCalSet.findByCid", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.cid = :cid"),
    @NamedQuery(name = "KshmtAutoCalSet.findByUpperLimitConfigOtTime", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.upperLimitConfigOtTime = :upperLimitConfigOtTime"),
    @NamedQuery(name = "KshmtAutoCalSet.findByCalAtr", query = "SELECT k FROM KshmtAutoCalSet k WHERE k.calAtr = :calAtr")})
public class KshmtAutoCalSet implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UPPER_LIMIT_CONFIG_OT_TIME")
    private short upperLimitConfigOtTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAL_ATR")
    private short calAtr;

    public KshmtAutoCalSet() {
    }

    public KshmtAutoCalSet(String cid) {
        this.cid = cid;
    }

    public KshmtAutoCalSet(String cid, int exclusVer, short upperLimitConfigOtTime, short calAtr) {
        this.cid = cid;
        this.exclusVer = exclusVer;
        this.upperLimitConfigOtTime = upperLimitConfigOtTime;
        this.calAtr = calAtr;
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

    public short getUpperLimitConfigOtTime() {
        return upperLimitConfigOtTime;
    }

    public void setUpperLimitConfigOtTime(short upperLimitConfigOtTime) {
        this.upperLimitConfigOtTime = upperLimitConfigOtTime;
    }

    public short getCalAtr() {
        return calAtr;
    }

    public void setCalAtr(short calAtr) {
        this.calAtr = calAtr;
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
        if (!(object instanceof KshmtAutoCalSet)) {
            return false;
        }
        KshmtAutoCalSet other = (KshmtAutoCalSet) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KshmtAutoCalSet[ cid=" + cid + " ]";
    }
    
}
