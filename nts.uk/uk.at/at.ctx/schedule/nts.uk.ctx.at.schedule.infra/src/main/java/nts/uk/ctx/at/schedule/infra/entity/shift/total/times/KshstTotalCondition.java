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
@Table(name = "KSHST_TOTAL_CONDITION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KshstTotalCondition.findAll", query = "SELECT k FROM KshstTotalCondition k"),
    @NamedQuery(name = "KshstTotalCondition.findByInsDate", query = "SELECT k FROM KshstTotalCondition k WHERE k.insDate = :insDate"),
    @NamedQuery(name = "KshstTotalCondition.findByInsCcd", query = "SELECT k FROM KshstTotalCondition k WHERE k.insCcd = :insCcd"),
    @NamedQuery(name = "KshstTotalCondition.findByInsScd", query = "SELECT k FROM KshstTotalCondition k WHERE k.insScd = :insScd"),
    @NamedQuery(name = "KshstTotalCondition.findByInsPg", query = "SELECT k FROM KshstTotalCondition k WHERE k.insPg = :insPg"),
    @NamedQuery(name = "KshstTotalCondition.findByUpdDate", query = "SELECT k FROM KshstTotalCondition k WHERE k.updDate = :updDate"),
    @NamedQuery(name = "KshstTotalCondition.findByUpdCcd", query = "SELECT k FROM KshstTotalCondition k WHERE k.updCcd = :updCcd"),
    @NamedQuery(name = "KshstTotalCondition.findByUpdScd", query = "SELECT k FROM KshstTotalCondition k WHERE k.updScd = :updScd"),
    @NamedQuery(name = "KshstTotalCondition.findByUpdPg", query = "SELECT k FROM KshstTotalCondition k WHERE k.updPg = :updPg"),
    @NamedQuery(name = "KshstTotalCondition.findByExclusVer", query = "SELECT k FROM KshstTotalCondition k WHERE k.exclusVer = :exclusVer"),
    @NamedQuery(name = "KshstTotalCondition.findByCid", query = "SELECT k FROM KshstTotalCondition k WHERE k.kshstTotalConditionPK.cid = :cid"),
    @NamedQuery(name = "KshstTotalCondition.findByTotalTimesNo", query = "SELECT k FROM KshstTotalCondition k WHERE k.kshstTotalConditionPK.totalTimesNo = :totalTimesNo"),
    @NamedQuery(name = "KshstTotalCondition.findByUpperLimitSetAtr", query = "SELECT k FROM KshstTotalCondition k WHERE k.upperLimitSetAtr = :upperLimitSetAtr"),
    @NamedQuery(name = "KshstTotalCondition.findByLowerLimitSetAtr", query = "SELECT k FROM KshstTotalCondition k WHERE k.lowerLimitSetAtr = :lowerLimitSetAtr"),
    @NamedQuery(name = "KshstTotalCondition.findByThresoldUpperLimit", query = "SELECT k FROM KshstTotalCondition k WHERE k.thresoldUpperLimit = :thresoldUpperLimit"),
    @NamedQuery(name = "KshstTotalCondition.findByThresoldLowerLimit", query = "SELECT k FROM KshstTotalCondition k WHERE k.thresoldLowerLimit = :thresoldLowerLimit")})
public class KshstTotalCondition implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KshstTotalConditionPK kshstTotalConditionPK;
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
    @Column(name = "UPPER_LIMIT_SET_ATR")
    private short upperLimitSetAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOWER_LIMIT_SET_ATR")
    private short lowerLimitSetAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "THRESOLD_UPPER_LIMIT")
    private short thresoldUpperLimit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "THRESOLD_LOWER_LIMIT")
    private short thresoldLowerLimit;

    public KshstTotalCondition() {
    }

    public KshstTotalCondition(KshstTotalConditionPK kshstTotalConditionPK) {
        this.kshstTotalConditionPK = kshstTotalConditionPK;
    }

    public KshstTotalCondition(KshstTotalConditionPK kshstTotalConditionPK, int exclusVer, short upperLimitSetAtr, short lowerLimitSetAtr, short thresoldUpperLimit, short thresoldLowerLimit) {
        this.kshstTotalConditionPK = kshstTotalConditionPK;
        this.exclusVer = exclusVer;
        this.upperLimitSetAtr = upperLimitSetAtr;
        this.lowerLimitSetAtr = lowerLimitSetAtr;
        this.thresoldUpperLimit = thresoldUpperLimit;
        this.thresoldLowerLimit = thresoldLowerLimit;
    }

    public KshstTotalCondition(String cid, short totalTimesNo) {
        this.kshstTotalConditionPK = new KshstTotalConditionPK(cid, totalTimesNo);
    }

    public KshstTotalConditionPK getKshstTotalConditionPK() {
        return kshstTotalConditionPK;
    }

    public void setKshstTotalConditionPK(KshstTotalConditionPK kshstTotalConditionPK) {
        this.kshstTotalConditionPK = kshstTotalConditionPK;
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

    public short getUpperLimitSetAtr() {
        return upperLimitSetAtr;
    }

    public void setUpperLimitSetAtr(short upperLimitSetAtr) {
        this.upperLimitSetAtr = upperLimitSetAtr;
    }

    public short getLowerLimitSetAtr() {
        return lowerLimitSetAtr;
    }

    public void setLowerLimitSetAtr(short lowerLimitSetAtr) {
        this.lowerLimitSetAtr = lowerLimitSetAtr;
    }

    public short getThresoldUpperLimit() {
        return thresoldUpperLimit;
    }

    public void setThresoldUpperLimit(short thresoldUpperLimit) {
        this.thresoldUpperLimit = thresoldUpperLimit;
    }

    public short getThresoldLowerLimit() {
        return thresoldLowerLimit;
    }

    public void setThresoldLowerLimit(short thresoldLowerLimit) {
        this.thresoldLowerLimit = thresoldLowerLimit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstTotalConditionPK != null ? kshstTotalConditionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstTotalCondition)) {
            return false;
        }
        KshstTotalCondition other = (KshstTotalCondition) object;
        if ((this.kshstTotalConditionPK == null && other.kshstTotalConditionPK != null) || (this.kshstTotalConditionPK != null && !this.kshstTotalConditionPK.equals(other.kshstTotalConditionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KshstTotalCondition[ kshstTotalConditionPK=" + kshstTotalConditionPK + " ]";
    }
    
}
