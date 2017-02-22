package nts.uk.ctx.pr.report.infra.salarydetail;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSettingRepository;

@Stateless
public class JpaSalaryPrintSettingRepository implements SalaryPrintSettingRepository {

	@Override
	public SalaryPrintSetting find(CompanyCode companyCode) {
		// TODO wait for entity
		return null;
	}

	@Override
	public void save(SalaryPrintSetting salaryPrintSetting) {
		// TODO wait for entity

	}

	@Override
	public void remove(SalaryPrintSetting salaryPrintSetting) {
		// TODO wait for entity

	}

}
