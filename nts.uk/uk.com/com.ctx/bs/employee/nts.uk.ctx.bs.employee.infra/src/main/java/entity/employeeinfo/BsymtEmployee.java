package entity.employeeinfo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import entity.employeeinfo.jobentryhistory.BsymtJobEntryHistory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BSYMT_EMPLOYEE")
public class BsymtEmployee extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BsymtEmployeePk bsydtEmployeePk;

	@Column(name = "PID")
	public String personId;

	@Column(name = "CID")
	public String companyId;

	@Column(name = "SCD")
	public String employeeCode;

	@Column(name = "COMPANY_MAIL")
	public String companyMail;

	@Column(name = "COMPANY_MOBILE_MAIL")
	public String companyMobileMail;

	@Column(name = "COMPANY_MOBILE")
	public String companyMobile;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="BsymtEmployee", orphanRemoval = true)
	public List<BsymtJobEntryHistory> listEntryHist;

	@Override
	protected Object getKey() {
		return this.bsydtEmployeePk;
	}
}
