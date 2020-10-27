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
@Table(name = "KRCMT_CALC_SET_FLEX")
@XmlRootElement
@NamedQueries({
//    @NamedQuery(name = "KrcmtCalcSetFlex.findAll", query = "SELECT k FROM KrcmtCalcSetFlex k"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByInsDate", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.insDate = :insDate"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByInsCcd", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.insCcd = :insCcd"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByInsScd", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.insScd = :insScd"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByInsPg", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.insPg = :insPg"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByUpdDate", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.updDate = :updDate"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByUpdCcd", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.updCcd = :updCcd"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByUpdScd", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.updScd = :updScd"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByUpdPg", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.updPg = :updPg"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByExclusVer", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.exclusVer = :exclusVer"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByFlexExcessTimeId", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.flexExcessTimeId = :flexExcessTimeId"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByFlexExcessTimeCalAtr", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.flexExcessTimeCalAtr = :flexExcessTimeCalAtr"),
//    @NamedQuery(name = "KrcmtCalcSetFlex.findByFlexExcessLimitSet", query = "SELECT k FROM KrcmtCalcSetFlex k WHERE k.flexExcessLimitSet = :flexExcessLimitSet")
	})
public class KrcmtCalcSetFlex extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "FLEX_EXCESS_TIME_ID")
    public String flexExcessTimeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLEX_EXCESS_TIME_CAL_ATR")
    public int flexExcessTimeCalAtr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLEX_EXCESS_LIMIT_SET")
    public int flexExcessLimitSet;

    public KrcmtCalcSetFlex() {
    }

    public KrcmtCalcSetFlex(String flexExcessTimeId) {
        this.flexExcessTimeId = flexExcessTimeId;
    }

    public KrcmtCalcSetFlex(String flexExcessTimeId, int flexExcessTimeCalAtr, int flexExcessLimitSet) {
        this.flexExcessTimeId = flexExcessTimeId;
        this.flexExcessTimeCalAtr = flexExcessTimeCalAtr;
        this.flexExcessLimitSet = flexExcessLimitSet;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flexExcessTimeId != null ? flexExcessTimeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcmtCalcSetFlex)) {
            return false;
        }
        KrcmtCalcSetFlex other = (KrcmtCalcSetFlex) object;
        if ((this.flexExcessTimeId == null && other.flexExcessTimeId != null) || (this.flexExcessTimeId != null && !this.flexExcessTimeId.equals(other.flexExcessTimeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcmtCalcSetFlex[ flexExcessTimeId=" + flexExcessTimeId + " ]";
    }

	@Override
	protected Object getKey() {
		return this.flexExcessTimeId;
	}
    
}
