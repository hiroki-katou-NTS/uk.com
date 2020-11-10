package nts.uk.ctx.at.function.ws.workledgeroutputitem;


import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.ws.WebService;
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

@Path("at/function/kwr/005")
@Produces("application/json")
public class WorkLedgerOutputItemWebService extends WebService {

    @Inject
    GetOutputSettingOfWorkLedgerQuery ofWorkLedgerQuery;

    @Inject
    GetSettingDetailWorkLedger getSettingDetailWorkLedger;

    @POST
    @Path("a/listworkledger")
    public List<WorkStatusOutputDto> getListWorkStatus(int setting) {
        return ofWorkLedgerQuery.getListWorkStatus(EnumAdaptor.valueOf(setting, SettingClassificationCommon.class));
    }
    @POST
    @Path("b/detailworkledger")
    public WorkLedgerOutputItem getDetail(String settingId) {
        return getSettingDetailWorkLedger.getDetail(settingId);
    }
}
