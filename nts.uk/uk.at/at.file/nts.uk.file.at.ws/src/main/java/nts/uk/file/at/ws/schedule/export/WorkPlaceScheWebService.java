package nts.uk.file.at.ws.schedule.export;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.file.at.app.schedule.export.WorkPlaceScheCheckFileParam;
import nts.uk.file.at.app.schedule.export.WorkPlaceScheDataSource;
import nts.uk.file.at.app.schedule.export.WorkPlaceScheExportService;

@Path("wpl/schedule/report")
@Produces("application/json") 
public class WorkPlaceScheWebService {
    
    @Inject
    private WorkPlaceScheExportService exportService;
    
    @POST
    @Path("print")
    public ExportServiceResult generate(WorkPlaceScheDataSource query) {
        return exportService.start(query);
    }
    
    @POST
    @Path("checkFileUpload")
    public void checkFile(WorkPlaceScheCheckFileParam checkFileParam) {
        
    }
}
