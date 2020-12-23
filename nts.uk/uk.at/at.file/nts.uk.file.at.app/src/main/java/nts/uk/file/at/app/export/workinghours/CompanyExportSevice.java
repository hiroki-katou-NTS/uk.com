package nts.uk.file.at.app.export.workinghours;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

/**
 * 
 * @author chungnt
 *
 */


@Stateless
public class CompanyExportSevice extends ExportService<YearsInput> {

	@Inject
	private CompanyTimeWorkExport companyExport;

	@Override
	protected void handle(ExportServiceContext<YearsInput> context) {
		this.companyExport.export(context.getGeneratorContext(), context.getQuery());
		
	}
}
