package nts.uk.ctx.pr.file.app.core.wageprovision.formula;

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
public class FormulaExportService extends ExportService<FormulaExportQuery> {

	@Inject
	private FormulaExRepository formulaExRepository;

	@Inject
	private FormulaFileGenerator formulaFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<FormulaExportQuery> exportServiceContext) {
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		String companyName = companyInfo.isPresent() ? companyInfo.get().getCompanyName() : "";
		String cid = AppContexts.user().companyId();
		List<Object[]> formulaInfor = new ArrayList<>();
		List<Object[]> formulaDetails = new ArrayList<>();
		List<Object[]> targetItems = new ArrayList<>();
		formulaInfor = formulaExRepository.getFormulaInfor(cid, exportServiceContext.getQuery().startDate);
		formulaDetails = formulaExRepository.getDetailFormula(cid);
		targetItems = formulaExRepository.getBaseAmountTargetItem(cid, exportServiceContext.getQuery().startDate);
		FormulaExportData data = new FormulaExportData(formulaInfor, formulaDetails , targetItems , companyName, exportServiceContext.getQuery().startDate );
		formulaFileGenerator.generate(exportServiceContext.getGeneratorContext(), data);
	}
}
