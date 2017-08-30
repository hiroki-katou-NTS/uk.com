package entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BSYDT_EMPLOYEE")
public class BsydtEmployee extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BsydtEmployeePk bsydtEmployeePk;
		
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@Column(name = "PERSON_ID")
	public String personID;

	@Basic(optional = false)
	@Column(name = "EMPLOYEE_CD")
	public String EmployeeCode;

	@Basic(optional = false)
	@Column(name = "COMPANY_MAIL")
	public String companyMail;
	
	@Basic(optional = false)
	@Column(name = "COMPANY_MOBILE_MAIL")
	public String companyMobileMail;
	
	@Basic(optional = false)
	@Column(name = "ENTRY_DATE")
	public String entryDate;
	
	@Basic(optional = false)
	@Column(name = "ADOPT_DATE")
	public String adoptDate;
	
	@Basic(optional = false)
	@Column(name = "HIRING_TYPE")
	public String hiringType;
	
	@Basic(optional = false)
	@Column(name = "RETIRE_DATE")
	public String retireDate;
	
	@Override
	protected Object getKey() {
		return this.bsydtEmployeePk;
	}
}
