package nts.uk.ctx.exio.ws.exo.execlog;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogDto;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogExportService;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogFinder;
import nts.uk.ctx.exio.app.find.exo.executionlog.ErrorContentDto;

@Path("exio/exo/execlog")
@Produces("application/json")
public class ExternalOutLogWebService extends WebService {

	@Inject
	private ExternalOutLogFinder externalOutLogFinder;

	@Inject
	private ExternalOutLogExportService exportService;

	@Path("getExternalOutLog/{storeProcessingId}")
	@POST
	public List<ExternalOutLogDto> getExternalOutLogById(@PathParam("storeProcessingId") String storeProcessingId) {
		return this.externalOutLogFinder.getExternalOutLogById(storeProcessingId);
	}

	@POST
	@Path("export")
	public ExportServiceResult exportCsvError(ErrorContentDto command) {
		return this.exportService.start(command);
	}

}
