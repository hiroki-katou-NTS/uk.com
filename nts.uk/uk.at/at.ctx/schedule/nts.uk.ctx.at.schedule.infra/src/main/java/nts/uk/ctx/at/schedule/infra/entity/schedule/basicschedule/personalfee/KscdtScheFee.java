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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscdtScheFee.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCDT_SCHE_FEE")
public class KscdtScheFee extends ContractUkJpaEntity  implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt ws person fee PK. */
    @EmbeddedId
    protected KscdtScheFeePK kscdtScheFeePK;
    
    /** The personal pee amount. */
    @NotNull
    @Column(name = "PERSONAL_FEE_AMOUNT")
    private int personalFeeAmount;

    public KscdtScheFee() {
    }

    public KscdtScheFee(KscdtScheFeePK kscmtWsPersonFeePK) {
        this.kscdtScheFeePK = kscmtWsPersonFeePK;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscdtScheFeePK != null ? kscdtScheFeePK.hashCode() : 0);
        return hash;
    }

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscdtScheFee)) {
			return false;
		}
		KscdtScheFee other = (KscdtScheFee) object;
		if ((this.kscdtScheFeePK == null && other.kscdtScheFeePK != null)
				|| (this.kscdtScheFeePK != null
						&& !this.kscdtScheFeePK.equals(other.kscdtScheFeePK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.KscmtWsPersonFee[ kscmtWsPersonFeePK=" + kscdtScheFeePK + " ]";
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscdtScheFeePK;
	}
    
}
