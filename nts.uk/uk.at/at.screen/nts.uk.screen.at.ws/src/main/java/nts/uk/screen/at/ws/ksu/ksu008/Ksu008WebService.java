package nts.uk.screen.at.ws.ksu.ksu008;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.command.form9.DuplicateItemOutputSettingInfoCommand;
import nts.uk.ctx.at.aggregation.app.command.form9.DuplicateItemOutputSettingInfoCommandHandler;
import nts.uk.ctx.at.aggregation.app.command.form9.UpdateDetailOutputSettingInfoCommand;
import nts.uk.ctx.at.aggregation.app.command.form9.UpdateDetailOutputSettingInfoCommandHandler;
import nts.uk.ctx.at.aggregation.app.find.form9.DetailOutputSettingInfoFinder;
import nts.uk.ctx.at.aggregation.app.find.form9.Form9DetailOutputSettingDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("screen/at/ksu/008/form9")
@Produces(MediaType.APPLICATION_JSON)
public class Ksu008WebService extends WebService {
    @Inject
    private DuplicateItemOutputSettingInfoCommandHandler duplicateHandler;

    @Inject
    private DetailOutputSettingInfoFinder detailFinder;

    @Inject
    private UpdateDetailOutputSettingInfoCommandHandler updateHandler;

    @POST
    @Path("duplicate")
    public void duplicate(DuplicateItemOutputSettingInfoCommand command) {
        this.duplicateHandler.handle(command);
    }

    @POST
    @Path("init-detail-setting")
    public Form9DetailOutputSettingDto getDetail() {
        return this.detailFinder.get();
    }

    @POST
    @Path("update-detail-setting")
    public void update(UpdateDetailOutputSettingInfoCommand command) {
        this.updateHandler.handle(command);
    }
}
