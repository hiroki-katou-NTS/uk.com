package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
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
	private SalaryHealthRepository salaryHealthRepository;

	@Inject
	private SalaryHealthFileGenerator salaryHealthFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<SalaryHealthExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		String cid = AppContexts.user().companyId();
		List<Object[]> socialInsurance = new ArrayList<>();
		socialInsurance = salaryHealthRepository.getSalaryHealth(cid);
		SalaryHealthExportData data = new SalaryHealthExportData(socialInsurance, companyName);
		salaryHealthFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}
}
