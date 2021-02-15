/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.entity.login;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class SgwdtContract.
 */
@Getter
@Setter
@Entity
@Table(name = "SGWDT_CONTRACT")

/**
 * Instantiates a new sgwdt contract.
 */
@NoArgsConstructor
public class SgwdtContract extends UkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** The contract cd. */
    @Id
    @Column(name = "CONTRACT_CD")
    private String contractCd;
    
    /** The password. */
    @Column(name = "PASSWORD")
    private String password;
    
    /** The str D. */
    @Column(name = "STR_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date strD;
    
    /** The end D. */
    @Column(name = "END_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endD;
    
    /**
     * Instantiates a new sgwdt contract.
     *
     * @param contractCd the contract cd
     */
    public SgwdtContract(String contractCd) {
        this.contractCd = contractCd;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contractCd != null ? contractCd.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SgwdtContract)) {
            return false;
        }
        SgwdtContract other = (SgwdtContract) object;
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
