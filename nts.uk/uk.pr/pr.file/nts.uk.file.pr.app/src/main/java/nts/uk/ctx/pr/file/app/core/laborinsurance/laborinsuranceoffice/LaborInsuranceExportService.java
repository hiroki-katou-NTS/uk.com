package nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class LaborInsuranceExportService extends ExportService<LaborInsuranceExportQuery> {

	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOffice;

	@Inject
	private LaborInsuranceFileGenerator laborInsuranceFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<LaborInsuranceExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		String cid = AppContexts.user().companyId();
		List<Object[]> laborInsuranceOff = laborInsuranceOffice.getLaborInsuranceOfficeByCompany(cid);
		LaborInsuranceExportData data = new LaborInsuranceExportData(laborInsuranceOff, companyName);
		laborInsuranceFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}
}
