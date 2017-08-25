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

/**
 * Gets the end D.
 *
 * @return the end D
 */
@Getter

/**
 * Sets the end D.
 *
 * @param endD the new end D
 */
@Setter
@Entity
@Table(name = "SGWDT_CONTRACT")

/**
 * Instantiates a new sgwdt contract.
 */
@NoArgsConstructor
public class SgwdtContract implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** The contract cd. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONTRACT_CD")
    private String contractCd;
    
    /** The password. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 66)
    @Column(name = "PASSWORD")
    private String password;
    
    /** The str D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date strD;
    
    /** The end D. */
    @Basic(optional = false)
    @NotNull
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

    /**
     * Instantiates a new sgwdt contract.
     *
     * @param contractCd the contract cd
     * @param exclusVer the exclus ver
     * @param password the password
     * @param strD the str D
     * @param endD the end D
     */
    public SgwdtContract(String contractCd, int exclusVer, String password, Date strD, Date endD) {
        this.contractCd = contractCd;
        this.password = password;
        this.strD = strD;
        this.endD = endD;
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
}
