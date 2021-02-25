package nts.uk.file.at.app.export.statement.stamp;

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
public class StampEdittingExportService extends ExportService<StampEdittingExportDatasource> {
	
	@Inject
	private StampEdittingExport stampEdittingExportService;
	
	@Override
	protected void handle(ExportServiceContext<StampEdittingExportDatasource> context) {
		
		StampEdittingExportDatasource datasource = context.getQuery();
		
		StampEdittingExportDatasource input = new StampEdittingExportDatasource(datasource.getDigitsNumber(), datasource.getStampMethod());
		
		this.stampEdittingExportService.export(context.getGeneratorContext(), input);
		
	}
}
