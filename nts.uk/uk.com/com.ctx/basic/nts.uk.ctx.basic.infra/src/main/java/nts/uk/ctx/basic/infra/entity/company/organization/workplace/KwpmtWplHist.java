/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


@Getter
@Setter
@Entity
@Table(name = "KWPMT_WPL_HIST")

public class KwpmtWplHist extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KwpmtWplHistPK kwpmtWplHistPK;
    
    @NotNull
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate strD;
    
    @NotNull
    @Column(name = "END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endD;

    public KwpmtWplHist() {
    }

    public KwpmtWplHist(KwpmtWplHistPK kwpmtWplHistPK) {
        this.kwpmtWplHistPK = kwpmtWplHistPK;
    }

    public KwpmtWplHist(KwpmtWplHistPK kwpmtWplHistPK, int exclusVer) {
        this.kwpmtWplHistPK = kwpmtWplHistPK;
    }

    public KwpmtWplHist(String cid, String histId) {
        this.kwpmtWplHistPK = new KwpmtWplHistPK(cid, histId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kwpmtWplHistPK != null ? kwpmtWplHistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KwpmtWplHist)) {
            return false;
        }
        KwpmtWplHist other = (KwpmtWplHist) object;
        if ((this.kwpmtWplHistPK == null && other.kwpmtWplHistPK != null) || (this.kwpmtWplHistPK != null && !this.kwpmtWplHistPK.equals(other.kwpmtWplHistPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KwpmtWplHist[ kwpmtWplHistPK=" + kwpmtWplHistPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.kwpmtWplHistPK;
	}
    
}
