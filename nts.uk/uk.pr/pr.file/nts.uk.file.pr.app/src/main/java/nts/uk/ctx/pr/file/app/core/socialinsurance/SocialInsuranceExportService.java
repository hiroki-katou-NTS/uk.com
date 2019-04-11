package nts.uk.ctx.pr.file.app.core.socialinsurance;

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
public class SocialInsuranceExportService extends ExportService<SocialInsuranceExportQuery> {

	@Inject
	private SocialInsuranceRepository socialInsuranceRepository;

	@Inject
	private SocialInsuranceFileGenerator socialInsuranceFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<SocialInsuranceExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		String cid = AppContexts.user().companyId();
		List<Object[]> socialInsurance = new ArrayList<>();
		if(Export.HEALTHY.value == exportServiceContext.getQuery().getExportType()) {
			socialInsurance = socialInsuranceRepository.getHeathyInsurance(cid);
		}
		if(Export.WELFARE_PENSION.value == exportServiceContext.getQuery().getExportType()) {
			socialInsurance = socialInsuranceRepository.getWelfarepensionInsurance(cid);
		}
		if(Export.SOCIAL_INSURANCE_OFFICE.value == exportServiceContext.getQuery().getExportType()) {
			socialInsurance = socialInsuranceRepository.getSocialInsuranceoffice(cid);
		}
		if(Export.CONTRIBUTION_RATE.value == exportServiceContext.getQuery().getExportType()) {
			socialInsurance = socialInsuranceRepository.getContributionRate(cid);
		}
		if(Export.SALARY_HEALTHY.value == exportServiceContext.getQuery().getExportType()) {
			socialInsurance = socialInsuranceRepository.getSalaryHealth(cid);
		}
		SocialInsuranceExportData data = new SocialInsuranceExportData(socialInsurance,null, companyName, exportServiceContext.getQuery().getExportType() );
		socialInsuranceFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}
}
