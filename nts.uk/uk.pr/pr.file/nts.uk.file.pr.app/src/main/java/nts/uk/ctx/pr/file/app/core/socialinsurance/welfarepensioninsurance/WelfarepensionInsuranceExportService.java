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
		List<Object[]> welfarepensioninsurance = new ArrayList<>();
		welfarepensioninsurance = welfarepensionInsuranceRepository.getWelfarepensionInsurance(cid);
		WelfarepensionInsuranceExportData data = new WelfarepensionInsuranceExportData(welfarepensioninsurance, companyName);
		welfarepensionInsuranceFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}
}
