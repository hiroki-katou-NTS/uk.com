package nts.uk.file.hr.app.databeforereflecting.retirementinformation;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.RetiDateDto;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.RetirementInformationFinder;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.SearchRetiredEmployeesQuery;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.SearchRetiredResultDto;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RetirementInformationExportService extends ExportService<SearchRetiredEmployeesQuery> {

	@Inject
	private RetirementInformationFinder finder;

	@Inject
	private RetirementInformationGenerator retiGenerator;

	@Inject
	private MandatoryRetirementRegulationService madaRepo;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<SearchRetiredEmployeesQuery> context) {

		SearchRetiredResultDto serchReti = this.finder.searchRetiredEmployees(context.getQuery());

		RetiDateDto retiDto = this.finder.startPage();

		String cId = AppContexts.user().companyId();
		List<ReferEvaluationItem> referEvaluaItems = this.madaRepo.getReferEvaluationItemByDate(cId,
				GeneralDate.today());

		this.retiGenerator.generate(context.getGeneratorContext(), serchReti, context.getQuery(),
				this.company.getCurrentCompany().get().getCompanyName(), referEvaluaItems, retiDto);

	}

}
