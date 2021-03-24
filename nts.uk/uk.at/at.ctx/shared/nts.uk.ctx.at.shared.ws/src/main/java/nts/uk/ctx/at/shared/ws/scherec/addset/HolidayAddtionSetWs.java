package nts.uk.ctx.at.shared.ws.scherec.addset;

import nts.uk.ctx.at.shared.app.command.scherec.addset.AddHolidayAddtionSetCommand;
import nts.uk.ctx.at.shared.app.command.scherec.addset.AddHolidayAddtionSetCommandHandler;
import nts.uk.ctx.at.shared.app.find.scherec.addset.HolidayAddSetDto;
import nts.uk.ctx.at.shared.app.find.scherec.addset.HolidayAddSetFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/shared/scherec/hdset")
@Produces("application/json")
public class HolidayAddtionSetWs {

    @Inject
    private HolidayAddSetFinder holidayAddSetFinder;

    @Inject
    private AddHolidayAddtionSetCommandHandler addHolidayAddtionSetCommandHandler;

    @POST
    @Path("getSetting")
    public HolidayAddSetDto getSetting() {
        return holidayAddSetFinder.init();
    }

    @POST
    @Path("save")
    public void save(AddHolidayAddtionSetCommand command) {
        addHolidayAddtionSetCommandHandler.handle(command);
    }

}
