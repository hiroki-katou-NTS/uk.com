package nts.uk.ctx.at.function.app.export.annualworkschedule;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleGenerator;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportDataSource;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AnnualWorkScheduleExportService extends ExportService<AnnualWorkScheduleExportQuery> {
	@Inject
	AnnualWorkScheduleRepository repostory;
	@Inject
	AnnualWorkScheduleGenerator generator;

	@Override
	protected void handle(ExportServiceContext<AnnualWorkScheduleExportQuery> context) {

		String companyId = AppContexts.user().companyId();
		AnnualWorkScheduleExportQuery query = context.getQuery();

		ExportData data = this.repostory.getData(companyId, query.getSetItemsOutputCd());
		List<ExportItem> exportItems = this.repostory.geExportItems();
		ExportDataSource dataSource = new ExportDataSource(exportItems, data);
		
		// invoke generator
		this.generator.generate(context.getGeneratorContext(), dataSource);
	}
	
}
