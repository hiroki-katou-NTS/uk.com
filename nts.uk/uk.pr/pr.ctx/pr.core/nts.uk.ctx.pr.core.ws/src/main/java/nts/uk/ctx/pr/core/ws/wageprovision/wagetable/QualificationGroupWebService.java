package nts.uk.ctx.pr.core.ws.wageprovision.wagetable;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.AddQualificationGroupSettingCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.DeleteQualificationGroupSettingCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.QualificationGroupSettingCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.UpdateQualificationGroupSettingCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.QualificationGroupSettingDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.QualificationGroupSettingFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.QualificationInformationFinder;

@Path("ctx/pr/core/wageprovision/wagetable")
@Produces("application/json")
public class QualificationGroupWebService {

    @Inject
    private QualificationGroupSettingFinder qualificationGroupSettingFinder;

    @Inject
    private QualificationInformationFinder qualificationInformationFinder;

    @Inject
    private AddQualificationGroupSettingCommandHandler addQualificationGroupSettingCommandHandler;

    @Inject
    private UpdateQualificationGroupSettingCommandHandler updateQualificationGroupSettingCommandHandler;

    @Inject
    private DeleteQualificationGroupSettingCommandHandler deleteQualificationGroupSettingCommandHandler;

    @POST
    @Path("/getAllQualificationGroup")
    public List<QualificationGroupSettingDto> getAllQualificationGroup() {
        return qualificationGroupSettingFinder.findByCompany();
    }

    @POST
    @Path("/getAllQualificationInformationExcludeUsed/{groupCode}")
    public List<String> getAllQualificationInformation(@PathParam("groupCode") String targetGroupCode) {
        return qualificationInformationFinder.findByCompanyExcludeUsed(targetGroupCode);
    }

    @POST
    @Path("/getAllQualificationGroupAndInformation")
    public List<Object> getAllQualificationGroupAndInformation() {
        return Arrays.asList(qualificationGroupSettingFinder.findByCompany(), qualificationInformationFinder.findByCompany());
    }

    @POST
    @Path("/getQualificationGroupByCode/{qualificationGroupCode}")
    public QualificationGroupSettingDto getQualificationGroupByCode(@PathParam("qualificationGroupCode") String qualificationGroupCode) {
        return qualificationGroupSettingFinder.findByQualificationGroupCode(qualificationGroupCode);
    }

    @POST
    @Path("/addQualificationGroup")
    public void addQualificationGroup(QualificationGroupSettingCommand command) {
        addQualificationGroupSettingCommandHandler.handle(command);
    }

    @POST
    @Path("/updateQualificationGroup")
    public void updateQualificationGroup(QualificationGroupSettingCommand command) {
        updateQualificationGroupSettingCommandHandler.handle(command);
    }

    @POST
    @Path("/deleteQualificationGroup")
    public void deleteQualificationGroup(QualificationGroupSettingCommand command) {
        deleteQualificationGroupSettingCommandHandler.handle(command);
    }
}
