/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.schedule.infra.entity.shift.total.times;

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
@Table(name = "KSHST_TOTAL_SUBJECTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshstTotalSubjects.findAll", query = "SELECT k FROM KshstTotalSubjects k"),
    @NamedQuery(name = "KshstTotalSubjects.findByInsDate", query = "SELECT k FROM KshstTotalSubjects k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshstTotalSubjects.findByInsCcd", query = "SELECT k FROM KshstTotalSubjects k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshstTotalSubjects.findByInsScd", query = "SELECT k FROM KshstTotalSubjects k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshstTotalSubjects.findByInsPg", query = "SELECT k FROM KshstTotalSubjects k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshstTotalSubjects.findByUpdDate", query = "SELECT k FROM KshstTotalSubjects k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshstTotalSubjects.findByUpdCcd", query = "SELECT k FROM KshstTotalSubjects k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshstTotalSubjects.findByUpdScd", query = "SELECT k FROM KshstTotalSubjects k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshstTotalSubjects.findByUpdPg", query = "SELECT k FROM KshstTotalSubjects k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshstTotalSubjects.findByExclusVer", query = "SELECT k FROM KshstTotalSubjects k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshstTotalSubjects.findByCid", query = "SELECT k FROM KshstTotalSubjects k WHERE k.kshstTotalSubjectsPK.cid = :cid"),
    @NamedQuery(name = "KshstTotalSubjects.findByTotalTimesNo", query = "SELECT k FROM KshstTotalSubjects k WHERE k.kshstTotalSubjectsPK.totalTimesNo = :totalTimesNo"),
    @NamedQuery(name = "KshstTotalSubjects.findByWorkTypeAtr", query = "SELECT k FROM KshstTotalSubjects k WHERE k.kshstTotalSubjectsPK.workTypeAtr = :workTypeAtr"),
    @NamedQuery(name = "KshstTotalSubjects.findByWorkTypeCd", query = "SELECT k FROM KshstTotalSubjects k WHERE k.workTypeCd = :workTypeCd")})
public class KshstTotalSubjects implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KshstTotalSubjectsPK kshstTotalSubjectsPK;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "WORK_TYPE_CD")
    private String workTypeCd;

    public KshstTotalSubjects() {
    }

    public KshstTotalSubjects(KshstTotalSubjectsPK kshstTotalSubjectsPK) {
        this.kshstTotalSubjectsPK = kshstTotalSubjectsPK;
    }

    public KshstTotalSubjects(KshstTotalSubjectsPK kshstTotalSubjectsPK, int exclusVer, String workTypeCd) {
        this.kshstTotalSubjectsPK = kshstTotalSubjectsPK;
        this.exclusVer = exclusVer;
        this.workTypeCd = workTypeCd;
    }

    public KshstTotalSubjects(String cid, short totalTimesNo, short workTypeAtr) {
        this.kshstTotalSubjectsPK = new KshstTotalSubjectsPK(cid, totalTimesNo, workTypeAtr);
    }

    public KshstTotalSubjectsPK getKshstTotalSubjectsPK() {
        return kshstTotalSubjectsPK;
    }

    public void setKshstTotalSubjectsPK(KshstTotalSubjectsPK kshstTotalSubjectsPK) {
        this.kshstTotalSubjectsPK = kshstTotalSubjectsPK;
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

    public String getWorkTypeCd() {
        return workTypeCd;
    }

    public void setWorkTypeCd(String workTypeCd) {
        this.workTypeCd = workTypeCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstTotalSubjectsPK != null ? kshstTotalSubjectsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstTotalSubjects)) {
            return false;
        }
        KshstTotalSubjects other = (KshstTotalSubjects) object;
        if ((this.kshstTotalSubjectsPK == null && other.kshstTotalSubjectsPK != null) || (this.kshstTotalSubjectsPK != null && !this.kshstTotalSubjectsPK.equals(other.kshstTotalSubjectsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KshstTotalSubjects[ kshstTotalSubjectsPK=" + kshstTotalSubjectsPK + " ]";
    }
    
}
