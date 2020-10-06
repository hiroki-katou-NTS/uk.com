package nts.uk.file.at.ws.stamp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.statement.stamp.StampEdittingExportDatasource;
import nts.uk.file.at.app.export.statement.stamp.StampEdittingExportService;

/**
 * 
 * @author chungnt
 *
 */

@Path("file/stampEditting/report")
@Produces("application/json")
public class StampEdittingExpostWS extends WebService {

	@Inject
	private StampEdittingExportService stampEdittingExport;
	
	@POST
	@Path("export")
	public ExportServiceResult generate(StampEdittingExportDatasource input) {
		return this.stampEdittingExport.start(input);
	}
}
