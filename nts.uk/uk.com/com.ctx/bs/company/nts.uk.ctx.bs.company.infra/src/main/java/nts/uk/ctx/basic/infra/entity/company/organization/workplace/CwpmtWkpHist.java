/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
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


/**
 * The Class CwpmtWkpHist.
 */
@Getter
@Setter
@Entity
@Table(name = "CWPMT_WKP_HIST")

public class CwpmtWkpHist extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CwpmtWkpHistPK cwpmtWkpHistPK;
    
    @NotNull
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate strD;
    
    @NotNull
    @Column(name = "END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endD;

    public CwpmtWkpHist() {
    }

    public CwpmtWkpHist(CwpmtWkpHistPK cwpmtWkpHistPK) {
        this.cwpmtWkpHistPK = cwpmtWkpHistPK;
    }

    public CwpmtWkpHist(CwpmtWkpHistPK cwpmtWkpHistPK, int exclusVer) {
        this.cwpmtWkpHistPK = cwpmtWkpHistPK;
    }

    public CwpmtWkpHist(String cid, String histId) {
        this.cwpmtWkpHistPK = new CwpmtWkpHistPK(cid, histId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cwpmtWkpHistPK != null ? cwpmtWkpHistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CwpmtWkpHist)) {
            return false;
        }
        CwpmtWkpHist other = (CwpmtWkpHist) object;
        if ((this.cwpmtWkpHistPK == null && other.cwpmtWkpHistPK != null) || (this.cwpmtWkpHistPK != null && !this.cwpmtWkpHistPK.equals(other.cwpmtWkpHistPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CwpmtWkpHist[ cwpmtWkpHistPK=" + cwpmtWkpHistPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.cwpmtWkpHistPK;
	}
    
}
