package nts.uk.ctx.at.function.ws.outputworkstatustable;


import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.outputworkstatustable.*;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.GetDetailOutputSettingWorkStatusQuery;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.GetOutputItemSettingQuery;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.WorkStatusOutputDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/function/kwr/003")
@Produces("application/json")
public class GetOutputItemSettingWebService extends WebService {

    @Inject
    private GetOutputItemSettingQuery settingQuery;

    @Inject
    private GetDetailOutputSettingWorkStatusQuery detailOutputSettingWorkStatusQuery;

    @Inject
    private CreateConfigdetailCommandHandler createConfigdetailCommandHandler;

    @Inject
    private UpdateSettingDetailCommandHandler updateSettingDetailCommandHandler;

    @Inject
    private DeleteDetailsOfTheWorkCommandHandler deleteDetailsOfTheWorkCommandHandler;

    @Inject
    private DuplicateSettingDetailCommandHandler duplicateSettingDetailCommandHandler;
    @POST
    @Path("b/listWorkStatus")
    public List<WorkStatusOutputDto> getListWorkStatus(int setting) {
        return settingQuery.getListWorkStatus(EnumAdaptor.valueOf(setting, SettingClassificationCommon.class));
    }
    @POST
    @Path("b/detailWorkStatus")
    public WorkStatusOutputSettings getDetailWorkStatus(String settingId) {
        return detailOutputSettingWorkStatusQuery.getDetail(settingId);
    }
    @POST
    @Path("b/create")
    public void create(CreateConfigdetailCommand command) {
        this.createConfigdetailCommandHandler.handle(command);
    }

    @POST
    @Path("b/update")
    public void create(UpdateSettingDetailCommand command) {
        this.updateSettingDetailCommandHandler.handle(command);
    }

    @POST
    @Path("b/delete")
    public void delete(DeleteDetailsOfTheWorkCommand command) {
        this.deleteDetailsOfTheWorkCommandHandler.handle(command);
    }
    @POST
    @Path("c/duplicate")
    public void duplicate(DuplicateSettingDetailCommand command) {
        this.duplicateSettingDetailCommandHandler.handle(command);
    }
    // TODO DTO->COMMAND
}