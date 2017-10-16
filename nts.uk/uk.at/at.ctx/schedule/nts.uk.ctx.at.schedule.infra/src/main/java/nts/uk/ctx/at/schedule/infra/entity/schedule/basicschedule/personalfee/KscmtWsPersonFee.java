/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtWsPersonFee.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_WS_PERSON_FEE")
public class KscmtWsPersonFee implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt ws person fee PK. */
    @EmbeddedId
    protected KscmtWsPersonFeePK kscmtWsPersonFeePK;
    
    /** The personal pee amount. */
    @NotNull
    @Column(name = "PERSONAL_PEE_AMOUNT")
    private int personalPeeAmount;

    public KscmtWsPersonFee() {
    }

    public KscmtWsPersonFee(KscmtWsPersonFeePK kscmtWsPersonFeePK) {
        this.kscmtWsPersonFeePK = kscmtWsPersonFeePK;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtWsPersonFeePK != null ? kscmtWsPersonFeePK.hashCode() : 0);
        return hash;
    }

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscmtWsPersonFee)) {
			return false;
		}
		KscmtWsPersonFee other = (KscmtWsPersonFee) object;
		if ((this.kscmtWsPersonFeePK == null && other.kscmtWsPersonFeePK != null)
				|| (this.kscmtWsPersonFeePK != null
						&& !this.kscmtWsPersonFeePK.equals(other.kscmtWsPersonFeePK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.KscmtWsPersonFee[ kscmtWsPersonFeePK=" + kscmtWsPersonFeePK + " ]";
	}
    
}
