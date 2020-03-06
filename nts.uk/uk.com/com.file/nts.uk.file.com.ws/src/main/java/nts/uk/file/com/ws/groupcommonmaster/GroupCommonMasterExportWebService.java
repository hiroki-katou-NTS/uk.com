package nts.uk.file.com.ws.groupcommonmaster;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.file.com.app.groupcommonmaster.GroupCommonMasterExportService;

@Path("file/com/report/groupcommonmaster/")
@Produces("application/json") 
public class GroupCommonMasterExportWebService {
	
	@Inject
	private GroupCommonMasterExportService service;
	
	@POST
	@Path("export")
	public ExportServiceResult generate() {
		return this.service.start(null);
	}
}
