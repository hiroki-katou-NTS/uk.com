package nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance;

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
public class HealthInsuranceExportService extends ExportService<HealthInsuranceExportQuery> {

	@Inject
	private HealthInsuranceRepository healthInsuranceRepository;

	@Inject
	private HealthInsuranceFileGenerator healthInsuranceFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<HealthInsuranceExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		String cid = AppContexts.user().companyId();
		List<Object[]> healthInsuranceMonth = new ArrayList<>();
		List<Object[]> bonusHealthInsuranceMonth = new ArrayList<>();
		healthInsuranceMonth = healthInsuranceRepository.getHeathyInsuranceMonth(cid, exportServiceContext.getQuery().startDate);
		bonusHealthInsuranceMonth = healthInsuranceRepository.getBonusHeathyInsurance(cid, exportServiceContext.getQuery().startDate);
		HealthInsuranceExportData data = new HealthInsuranceExportData(healthInsuranceMonth,bonusHealthInsuranceMonth, companyName, exportServiceContext.getQuery().startDate );
		healthInsuranceFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}
}
