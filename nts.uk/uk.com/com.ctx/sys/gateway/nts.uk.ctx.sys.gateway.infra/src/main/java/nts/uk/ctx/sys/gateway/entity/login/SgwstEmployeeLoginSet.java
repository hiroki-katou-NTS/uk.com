/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.entity.login;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "SGWST_EMPLOYEE_LOGIN_SET")
@NoArgsConstructor
public class SgwstEmployeeLoginSet extends UkJpaEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "CONTRACT_CD")
    private String contractCd;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FORM2_PERMIT_ATR")
    private short form2PermitAtr;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FORM3_PERMIT_ATR")
    private short form3PermitAtr;

    public SgwstEmployeeLoginSet(String contractCd) {
        this.contractCd = contractCd;
    }

    public SgwstEmployeeLoginSet(String contractCd, int exclusVer, short form2PermitAtr, short form3PermitAtr) {
        this.contractCd = contractCd;
        this.form2PermitAtr = form2PermitAtr;
        this.form3PermitAtr = form3PermitAtr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contractCd != null ? contractCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgwstEmployeeLoginSet)) {
            return false;
        }
        SgwstEmployeeLoginSet other = (SgwstEmployeeLoginSet) object;
        if ((this.contractCd == null && other.contractCd != null) || (this.contractCd != null && !this.contractCd.equals(other.contractCd))) {
            return false;
        }
        return true;
    }

	@Override
	protected Object getKey() {
		return this.contractCd;
	}
    
}
