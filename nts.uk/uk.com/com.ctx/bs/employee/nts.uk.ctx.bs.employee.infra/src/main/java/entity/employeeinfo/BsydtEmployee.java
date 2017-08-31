package entity.employeeinfo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "BSYDT_EMPLOYEE")
public class BsydtEmployee extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BsydtEmployeePk bsydtEmployeePk;
	
	@Basic(optional = false)
	@Column(name = "PID")
	public String personId;
		
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@Column(name = "SCD")
	public String employeeCode;

	@Basic(optional = false)
	@Column(name = "COMPANY_MAIL")
	public String companyMail;
	
	@Basic(optional = false)
	@Column(name = "COMPANY_MOBILE_MAIL")
	public String companyMobileMail;
	
	@Basic(optional = false)
	@Column(name = "COMPANY_MOBILE")
	public String companyMobile;
	
	@Basic(optional = false)
	@Column(name = "ENTRY_DATE")
	public GeneralDate entryDate;
	
	@Basic(optional = true)
	@Column(name = "ADOPT_DATE")
	public GeneralDate adoptDate;
	
	@Basic(optional = true)
	@Column(name = "HIRING_TYPE")
	public String hiringType;
	
	@Basic(optional = true)
	@Column(name = "RETIRE_DATE")
	public GeneralDate retireDate;
	
	@Override
	protected Object getKey() {
		return this.bsydtEmployeePk;
	}
}
