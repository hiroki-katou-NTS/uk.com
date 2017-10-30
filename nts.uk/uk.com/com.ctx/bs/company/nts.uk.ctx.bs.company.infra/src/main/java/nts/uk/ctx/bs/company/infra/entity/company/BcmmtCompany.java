/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.infra.entity.company;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class CmnmtCompany.
 */
@Getter
@Setter
@Entity
@Table(name = "BCMMT_COMPANY")
public class BcmmtCompany extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The ccd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CCD")
    private String ccd;

    /** The ccd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPANY_NAME")
    private String companyName;
    
    /** The cid. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The str M. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_M")
    private Integer strM;
    
    
    /** The Abolition. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ABOLITION_ATR")
    public int abolitionAtr;
    
    /** 人事システム */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERSONAL_SYSTEM")
    public int personSystem;
    
    /** 就業システム  */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPLOYMENT_SYSTEM")
    public int employmentSystem;
    
    /** 給与システム */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYROLL_SYSTEM")
    public int payrollSystem;

    
    /**
     * Instantiates a new cmnmt company.
     */
    public BcmmtCompany() {
    	super();
    }

    /**
     * Instantiates a new cmnmt company.
     *
     * @param cid the cid
     */
    public BcmmtCompany(String cid) {
        this.cid = cid;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof BcmmtCompany)) {
			return false;
		}
		BcmmtCompany other = (BcmmtCompany) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getCid();
	}
    
}
