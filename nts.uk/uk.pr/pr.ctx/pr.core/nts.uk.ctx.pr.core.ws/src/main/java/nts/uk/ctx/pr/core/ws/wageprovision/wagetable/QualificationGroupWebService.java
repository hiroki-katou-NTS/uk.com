package nts.uk.ctx.pr.core.ws.wageprovision.wagetable;

import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.QualificationGroupSettingCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.WageTableHistoryCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.QualificationGroupSettingDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.QualificationInformationDto;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.*;

@Path("ctx/pr/core/wageprovision/wagetable")
@Produces("application/json")
public class QualificationGroupWebService {

    @POST
    @Path("/getAllQualificationGroup")
    public List<QualificationGroupSettingDto> getAllQualificationGroup() {
        return Collections.emptyList();
    }

    @POST
    @Path("/getAllQualificationInformation")
    public List<QualificationInformationDto> getAllQualificationInformation() {
        return Collections.emptyList();
    }

    @POST
    @Path("/getAllQualificationGroupAndInformation")
    public List<List<Object>> getAllQualificationGroupAndInformation() {
        return Arrays.asList(Collections.emptyList(), Collections.emptyList());
    }

    @POST
    @Path("/getQualificationGroupByCode/{qualificationGroupCode}")
    public QualificationInformationDto getQualificationGroupByCode(@PathParam("qualificationGroupCode") String qualificationGroupCode) {
        return null;
    }

    @POST
    @Path("/addQualificationGroup")
    public void addQualificationGroup(QualificationGroupSettingCommand command) {

    }

    @POST
    @Path("/updateQualificationGroup")
    public void updateQualificationGroup(QualificationGroupSettingCommand command) {

    }

    @POST
    @Path("/deleteQualificationGroup")
    public void deleteQualificationGroup(QualificationGroupSettingCommand command) {

    }
}
