package nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance;

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
public class WelfarepensionInsuranceExportService extends ExportService<WelfarepensionInsuranceExportQuery> {

	@Inject
	private WelfarepensionInsuranceRepository welfarepensionInsuranceRepository;

	@Inject
	private WelfarepensionInsuranceFileGenerator welfarepensionInsuranceFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<WelfarepensionInsuranceExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		String cid = AppContexts.user().companyId();
		List<Object[]> welfarepensioninsuranceEmp = new ArrayList<>();
		List<Object[]> welfarepensioninsuranceBonus = new ArrayList<>();
		welfarepensioninsuranceEmp = welfarepensionInsuranceRepository.getWelfarepensionInsuranceEmp(cid, exportServiceContext.getQuery().getStartDate());
		welfarepensioninsuranceBonus = welfarepensionInsuranceRepository.getWelfarepensionInsuranceBonus(cid, exportServiceContext.getQuery().getStartDate());
		WelfarepensionInsuranceExportData data = new WelfarepensionInsuranceExportData(welfarepensioninsuranceEmp, welfarepensioninsuranceBonus, companyName, exportServiceContext.getQuery().getStartDate());
		welfarepensionInsuranceFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}
}
