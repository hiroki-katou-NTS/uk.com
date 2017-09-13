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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BSYMT_EMPLOYEE")
public class BsymtEmployee extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BsymtEmployeePk bsydtEmployeePk;

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

	@Override
	protected Object getKey() {
		return this.bsydtEmployeePk;
	}
}
