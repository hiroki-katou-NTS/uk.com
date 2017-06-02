package nts.uk.ctx.basic.app.find.company.organization.employment.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentCode;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentName;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentSetMemento;

@Getter
@Setter
public class EmploymentFindDto implements EmploymentSetMemento{
	
	private String code;
	private String name;

	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkDaysPerMonth(Integer workDaysPerMonth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPaymentDay(Integer paymentDay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.code = employmentCode.v();
	}

	@Override
	public void setEmploymentName(EmploymentName employmentName) {
		this.name = employmentName.v();
	}

}
