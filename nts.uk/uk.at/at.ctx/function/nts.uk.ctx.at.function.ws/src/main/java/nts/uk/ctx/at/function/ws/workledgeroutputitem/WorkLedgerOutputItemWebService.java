package nts.uk.ctx.at.function.ws.workledgeroutputitem;


import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.workledgeroutputitem.*;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.WorkStatusOutputDto;
import nts.uk.ctx.at.function.app.query.workledgeroutputitem.GetOutputSettingOfWorkLedgerQuery;
import nts.uk.ctx.at.function.app.query.workledgeroutputitem.GetSettingDetailWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/function/kwr")
@Produces("application/json")
public class WorkLedgerOutputItemWebService extends WebService {

    @Inject
    private GetOutputSettingOfWorkLedgerQuery ofWorkLedgerQuery;

    @Inject
    private GetSettingDetailWorkLedger getSettingDetailWorkLedger;

    @Inject
    private CreateWorkLedgerSettingCommandHandler createWorkLedgerSettingCommandHandler;

    @Inject
    private UpdateWorkLedgerSettingCommandHandler updateWorkLedgerSettingCommandHandler;

    @Inject
    private DuplicateWorkLedgerSettingCommandHandler duplicateCommandHandler;

    @Inject
    private DeleteWorkLedgerSettingCommandHandler deleteCommandHandler;

    @POST
    @Path("005/a/listworkledger")
    public List<WorkStatusOutputDto> getListWorkStatus(int setting) {
        return ofWorkLedgerQuery.getListWorkStatus(EnumAdaptor.valueOf(setting, SettingClassificationCommon.class));
    }
    @POST
    @Path("005/b/detailworkledger")
    public WorkLedgerOutputItem getDetail(String settingId) {
        return getSettingDetailWorkLedger.getDetail(settingId);
    }

    @POST
    @Path("005/b/create")
    public void create(CreateWorkLedgerSettingCommand dto) {
        createWorkLedgerSettingCommandHandler.handle(dto);
    }

    @POST
    @Path("005/b/update")
    public void update(UpdateWorkLedgerSettingCommand dto) {
        updateWorkLedgerSettingCommandHandler.handle(dto);
    }

    @POST
    @Path("005/b/delete")
    public void delete(DeleteWorkLedgerSettingCommand dto) {
        deleteCommandHandler.handle(dto);
    }

    @POST
    @Path("005/c/duplicate")
    public void duplicate(DuplicateWorkLedgerSettingCommand dto) {
        duplicateCommandHandler.handle(dto);
    }
}
