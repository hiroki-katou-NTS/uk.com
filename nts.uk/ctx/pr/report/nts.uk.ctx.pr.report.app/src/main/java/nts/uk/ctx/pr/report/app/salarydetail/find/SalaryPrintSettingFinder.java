package nts.uk.ctx.pr.report.app.salarydetail.find;

import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSettingRepository;

public class SalaryPrintSettingFinder {

	@Inject
	private SalaryPrintSettingRepository repository;

	public SalaryPrintSettingDto find(CompanyCode companyCode) {
		// TODO implement later
		return null;
	}
}
