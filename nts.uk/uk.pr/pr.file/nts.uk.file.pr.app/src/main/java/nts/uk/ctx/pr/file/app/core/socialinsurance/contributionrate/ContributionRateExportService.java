package nts.uk.ctx.pr.file.app.core.socialinsurance.contributionrate;

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
public class ContributionRateExportService extends ExportService<ContributionRateExportQuery> {

	@Inject
	private ContributionRateExRepository contributionRateRepository;

	@Inject
	private ContributionRateFileGenerator contributionRateFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<ContributionRateExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		String cid = AppContexts.user().companyId();
		List<Object[]> contributionRate = new ArrayList<>();
		contributionRate = contributionRateRepository.getContributionRate(cid);
		ContributionRateExportData data = new ContributionRateExportData(contributionRate, companyName);
		contributionRateFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}
}
