package nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance;

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
public class LifeInsuranceExportService extends ExportService<LifeInsuranceExportQuery> {

	@Inject
	private LifeInsuranceExRepository lifeInsuranceExRepository;

	@Inject
	private LifeInsuranceFileGenerator lifeInsuranceFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<LifeInsuranceExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		String cid = AppContexts.user().companyId();
		List<Object[]> lifeInsurance = new ArrayList<>();
		lifeInsurance = lifeInsuranceExRepository.getLifeInsurances(cid);
		LifeInsuranceExportData data = new LifeInsuranceExportData(lifeInsurance, companyName);
		lifeInsuranceFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}
}
