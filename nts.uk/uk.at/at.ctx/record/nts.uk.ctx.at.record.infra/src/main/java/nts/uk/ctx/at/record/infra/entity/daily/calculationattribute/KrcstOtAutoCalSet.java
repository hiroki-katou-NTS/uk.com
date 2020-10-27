/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.daily.calculationattribute;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Entity
@Table(name = "KRCST_OT_AUTO_CAL_SET")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "KrcstOtAutoCalSet.findAll", query = "SELECT k FROM KrcstOtAutoCalSet k"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByInsDate", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.insDate = :insDate"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByInsCcd", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.insCcd = :insCcd"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByInsScd", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.insScd = :insScd"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByInsPg", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.insPg = :insPg"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByUpdDate", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.updDate = :updDate"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByUpdCcd", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.updCcd = :updCcd"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByUpdScd", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.updScd = :updScd"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByUpdPg", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.updPg = :updPg"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByExclusVer", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.exclusVer = :exclusVer"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByOverTimeWorkId", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.overTimeWorkId = :overTimeWorkId"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByEarlyOverTimeCalAtr", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.earlyOverTimeCalAtr = :earlyOverTimeCalAtr"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByEarlyOverTimeLimitSet", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.earlyOverTimeLimitSet = :earlyOverTimeLimitSet"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByEarlyMidOtCalAtr", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.earlyMidOtCalAtr = :earlyMidOtCalAtr"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByEarlyMidOtLimitSet", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.earlyMidOtLimitSet = :earlyMidOtLimitSet"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByNormalOverTimeCalAtr", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.normalOverTimeCalAtr = :normalOverTimeCalAtr"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByNormalOverTimeLimitSet", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.normalOverTimeLimitSet = :normalOverTimeLimitSet"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByNormalMidOtCalAtr", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.normalMidOtCalAtr = :normalMidOtCalAtr"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByNormalMidOtLimitSet", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.normalMidOtLimitSet = :normalMidOtLimitSet"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByLegalOverTimeCalAtr", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.legalOverTimeCalAtr = :legalOverTimeCalAtr"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByLegalOverTimeLimitSet", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.legalOverTimeLimitSet = :legalOverTimeLimitSet"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByLegalMidOtCalAtr", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.legalMidOtCalAtr = :legalMidOtCalAtr"),
//    @NamedQuery(name = "KrcstOtAutoCalSet.findByLegalMidOtLimitSet", query = "SELECT k FROM KrcstOtAutoCalSet k WHERE k.legalMidOtLimitSet = :legalMidOtLimitSet")
	})
public class KrcstOtAutoCalSet extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "OVER_TIME_WORK_ID")
    public String overTimeWorkId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EARLY_OVER_TIME_CAL_ATR")
    public int earlyOverTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EARLY_OVER_TIME_LIMIT_SET")
    public int earlyOverTimeLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EARLY_MID_OT_CAL_ATR")
    public int earlyMidOtCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EARLY_MID_OT_LIMIT_SET")
    public int earlyMidOtLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_OVER_TIME_CAL_ATR")
    public int normalOverTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_OVER_TIME_LIMIT_SET")
    public int normalOverTimeLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_MID_OT_CAL_ATR")
    public int normalMidOtCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORMAL_MID_OT_LIMIT_SET")
    public int normalMidOtLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_OVER_TIME_CAL_ATR")
    public int legalOverTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_OVER_TIME_LIMIT_SET")
    public int legalOverTimeLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_MID_OT_CAL_ATR")
    public int legalMidOtCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEGAL_MID_OT_LIMIT_SET")
    public int legalMidOtLimitSet;

    public KrcstOtAutoCalSet() {
    }

    public KrcstOtAutoCalSet(String overTimeWorkId) {
        this.overTimeWorkId = overTimeWorkId;
    }

    public KrcstOtAutoCalSet(String overTimeWorkId, int earlyOverTimeCalAtr, int earlyOverTimeLimitSet, int earlyMidOtCalAtr, 
    		int earlyMidOtLimitSet, int normalOverTimeCalAtr, int normalOverTimeLimitSet, int normalMidOtCalAtr, int normalMidOtLimitSet, 
    		int legalOverTimeCalAtr, int legalOverTimeLimitSet, int legalMidOtCalAtr, int legalMidOtLimitSet) {
        this.overTimeWorkId = overTimeWorkId;
        this.earlyOverTimeCalAtr = earlyOverTimeCalAtr;
        this.earlyOverTimeLimitSet = earlyOverTimeLimitSet;
        this.earlyMidOtCalAtr = earlyMidOtCalAtr;
        this.earlyMidOtLimitSet = earlyMidOtLimitSet;
        this.normalOverTimeCalAtr = normalOverTimeCalAtr;
        this.normalOverTimeLimitSet = normalOverTimeLimitSet;
        this.normalMidOtCalAtr = normalMidOtCalAtr;
        this.normalMidOtLimitSet = normalMidOtLimitSet;
        this.legalOverTimeCalAtr = legalOverTimeCalAtr;
        this.legalOverTimeLimitSet = legalOverTimeLimitSet;
        this.legalMidOtCalAtr = legalMidOtCalAtr;
        this.legalMidOtLimitSet = legalMidOtLimitSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (overTimeWorkId != null ? overTimeWorkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KrcstOtAutoCalSet)) {
            return false;
        }
        KrcstOtAutoCalSet other = (KrcstOtAutoCalSet) object;
        if ((this.overTimeWorkId == null && other.overTimeWorkId != null) || (this.overTimeWorkId != null && !this.overTimeWorkId.equals(other.overTimeWorkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstOtAutoCalSet[ overTimeWorkId=" + overTimeWorkId + " ]";
    }

	@Override
	protected Object getKey() {
		return this.overTimeWorkId;
	}
    
}
