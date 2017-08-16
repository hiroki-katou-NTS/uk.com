/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.entity.login;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class SgwdtContract.
 */
@Getter
@Setter
@Entity
@Table(name = "SGWDT_CONTRACT")
@NoArgsConstructor
public class SgwdtContract implements Serializable {
	
    private static final long serialVersionUID = 1L;
   
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONTRACT_CD")
    private String contractCd;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 66)
    @Column(name = "PASSWORD")
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date strD;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endD;
    
    public SgwdtContract(String contractCd) {
        this.contractCd = contractCd;
    }

    public SgwdtContract(String contractCd, int exclusVer, String password, Date strD, Date endD) {
        this.contractCd = contractCd;
        this.password = password;
        this.strD = strD;
        this.endD = endD;
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
        if (!(object instanceof SgwdtContract)) {
            return false;
        }
        SgwdtContract other = (SgwdtContract) object;
        if ((this.contractCd == null && other.contractCd != null) || (this.contractCd != null && !this.contractCd.equals(other.contractCd))) {
            return false;
        }
        return true;
    }
}
