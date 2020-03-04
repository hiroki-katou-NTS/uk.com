package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.WelfarePensionStandardMonthlyFeeFinder;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.CusWelfarePensionStandardDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.ResponseWelfarePension;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.StartCommandHealth;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class SalaryHealthExportService extends ExportService<SalaryHealthExportQuery> {


	@Inject
	private SalaryHealthFileGenerator salaryHealthFileGenerator;

	@Inject
	private CompanyAdapter company;


	@Inject
	private WelfarePensionStandardMonthlyFeeFinder feeFinder;





	@Override
	protected void handle(ExportServiceContext<SalaryHealthExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		SalaryHealthExportQuery query = exportServiceContext.getQuery();
		StartCommandHealth startCommandHealth = new StartCommandHealth(query.getHistoryId(),query.getDate(),query.getSocialInsuranceCode());
		ResponseWelfarePension exportData =  feeFinder.findAllWelfarePensionAndRate(startCommandHealth,false);
		List<CusWelfarePensionStandardDto> list = feeFinder.findAllWelfarePensionAndContributionRate(startCommandHealth,false,query.getDate());
		SalaryHealthExportData data = new SalaryHealthExportData(exportData, companyName);
		salaryHealthFileGenerator.generate(exportServiceContext.getGeneratorContext(), data, list,query.getSocialInsuranceCode(),query.getSocialInsuranceName());
	}
}
