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
@Table(name = "KRCST_HOL_AUTO_CAL_SET")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "KrcstHolAutoCalSet.findAll", query = "SELECT k FROM KrcstHolAutoCalSet k"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByInsDate", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.insDate = :insDate"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByInsCcd", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.insCcd = :insCcd"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByInsScd", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.insScd = :insScd"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByInsPg", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.insPg = :insPg"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByUpdDate", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.updDate = :updDate"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByUpdCcd", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.updCcd = :updCcd"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByUpdScd", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.updScd = :updScd"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByUpdPg", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.updPg = :updPg"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByExclusVer", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.exclusVer = :exclusVer"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByHolWorkTimeId", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.holWorkTimeId = :holWorkTimeId"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByHolWorkTimeCalAtr", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.holWorkTimeCalAtr = :holWorkTimeCalAtr"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByHolWorkTimeLimitSet", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.holWorkTimeLimitSet = :holWorkTimeLimitSet"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByLateNightTimeCalAtr", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.lateNightTimeCalAtr = :lateNightTimeCalAtr"),
//    @NamedQuery(name = "KrcstHolAutoCalSet.findByLateNightTimeLimitSet", query = "SELECT k FROM KrcstHolAutoCalSet k WHERE k.lateNightTimeLimitSet = :lateNightTimeLimitSet")
	})
public class KrcstHolAutoCalSet extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "HOL_WORK_TIME_ID")
    public String holWorkTimeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HOL_WORK_TIME_CAL_ATR")
    public int holWorkTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HOL_WORK_TIME_LIMIT_SET")
    public int holWorkTimeLimitSet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LATE_NIGHT_TIME_CAL_ATR")
    public int lateNightTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LATE_NIGHT_TIME_LIMIT_SET")
    public int lateNightTimeLimitSet;

    public KrcstHolAutoCalSet() {
    }

    public KrcstHolAutoCalSet(String holWorkTimeId) {
        this.holWorkTimeId = holWorkTimeId;
    }

    public KrcstHolAutoCalSet(String holWorkTimeId, int holWorkTimeCalAtr, int holWorkTimeLimitSet, int lateNightTimeCalAtr, int lateNightTimeLimitSet) {
        this.holWorkTimeId = holWorkTimeId;
        this.holWorkTimeCalAtr = holWorkTimeCalAtr;
        this.holWorkTimeLimitSet = holWorkTimeLimitSet;
        this.lateNightTimeCalAtr = lateNightTimeCalAtr;
        this.lateNightTimeLimitSet = lateNightTimeLimitSet;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (holWorkTimeId != null ? holWorkTimeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstHolAutoCalSet)) {
            return false;
        }
        KrcstHolAutoCalSet other = (KrcstHolAutoCalSet) object;
        if ((this.holWorkTimeId == null && other.holWorkTimeId != null) || (this.holWorkTimeId != null && !this.holWorkTimeId.equals(other.holWorkTimeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcstHolAutoCalSet[ holWorkTimeId=" + holWorkTimeId + " ]";
    }

	@Override
	protected Object getKey() {
		return holWorkTimeId;
	}
    
}
