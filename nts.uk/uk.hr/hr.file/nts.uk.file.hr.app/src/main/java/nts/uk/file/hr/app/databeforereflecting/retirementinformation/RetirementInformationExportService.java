package nts.uk.file.hr.app.databeforereflecting.retirementinformation;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.find.RetirementInformationFinder;
import nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.find.SearchRetiredEmployeesQuery;
import nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.find.SearchRetiredResultDto;
import nts.uk.shr.com.company.CompanyAdapter;

@Stateless
public class RetirementInformationExportService extends ExportService<SearchRetiredEmployeesQuery> {
	
	@Inject
	private RetirementInformationFinder finder;
	
	@Inject
	private RetirementInformationGenerator retiGenerator;
	
	 @Inject
	    private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<SearchRetiredEmployeesQuery> context) {

		SearchRetiredResultDto serchReti = this.finder.searchRetiredEmployees(context.getQuery());
		
		this.retiGenerator.generate(context.getGeneratorContext(), serchReti, context.getQuery(),
				this.company.getCurrentCompany().get().getCompanyName());
		
		
	}

}
