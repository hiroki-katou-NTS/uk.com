package nts.uk.ctx.pr.report.app.salarydetail.find;

import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSettingRepository;

public class SalaryPrintSettingFinder {

	@Inject
	private SalaryPrintSettingRepository repository;

	public SalaryPrintSettingDto find(CompanyCode companyCode) {
		SalaryPrintSetting salaryPrintSetting = repository.find(companyCode);
		//TODO convert to Dto
		return null;
	}
}
