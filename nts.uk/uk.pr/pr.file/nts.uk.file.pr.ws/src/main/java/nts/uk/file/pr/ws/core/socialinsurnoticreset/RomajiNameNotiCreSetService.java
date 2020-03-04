package nts.uk.file.pr.ws.core.socialinsurnoticreset;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.RomajiNameNotiCreSetExportPDFService;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.RomajiNameNotiCreSetExportQuery;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.RomajiNameNotiCreSetDto;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.RomajiNameNotiCreSetFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/file/printconfig/romaji")
@Produces("application/json")
public class RomajiNameNotiCreSetService {
    @Inject
    private RomajiNameNotiCreSetFinder finder;

    @Inject
    private RomajiNameNotiCreSetExportPDFService service;

    @POST
    @Path("/getRomajiNameNoti")
    public RomajiNameNotiCreSetDto getRomajiNameNotiCreSettingById(){
        return finder.getRomajiNameNotiCreSettingById();
    }

    @POST
    @Path("/exportData")
    public ExportServiceResult exportData(RomajiNameNotiCreSetExportQuery query){
        return service.start(query);
    }
}
