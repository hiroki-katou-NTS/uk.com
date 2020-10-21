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
		int digitsNumber = datasource.getDigitsNumber();
		int stampMethod = datasource.getStampMethod();
		
		StampEdittingExportDatasource input = new StampEdittingExportDatasource(digitsNumber, stampMethod);
		
		this.stampEdittingExportService.export(context.getGeneratorContext(), input);
		
	}
}
