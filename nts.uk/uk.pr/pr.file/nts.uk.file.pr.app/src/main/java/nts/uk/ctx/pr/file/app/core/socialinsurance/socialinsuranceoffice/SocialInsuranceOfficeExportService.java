package nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice;

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
public class SocialInsuranceOfficeExportService extends ExportService<SocialInsuranceOfficeExportQuery> {

	@Inject
	private SocialInsuranceOfficeExRepository socialInsuranceRepository;

	@Inject
	private SocialInsuranceOfficeFileGenerator socialInsuranceFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<SocialInsuranceOfficeExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		String cid = AppContexts.user().companyId();
		List<Object[]> socialInsurance = new ArrayList<>();
		socialInsurance = socialInsuranceRepository.getSocialInsuranceoffice(cid);
		SocialInsuranceOfficeExportData data = new SocialInsuranceOfficeExportData(socialInsurance, companyName);
		socialInsuranceFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}
}
