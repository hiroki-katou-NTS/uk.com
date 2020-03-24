package nts.uk.file.at.app.export.statement.stamp;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
@Stateless
public class OutputListOfStampExportService extends ExportService<OutputConditionListOfStampQuery> {
	
	@Inject
	OutputConditionListOfStampGenerator generator;
	
	@Override
	protected void handle(ExportServiceContext<OutputConditionListOfStampQuery> context) {
		OutputConditionListOfStampQuery query = context.getQuery();
		this.generator.generate(context.getGeneratorContext(), query);
		
	}
}
