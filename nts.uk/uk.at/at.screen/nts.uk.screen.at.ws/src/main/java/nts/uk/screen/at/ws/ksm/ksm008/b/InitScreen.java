package nts.uk.screen.at.ws.ksm.ksm008.b;

import nts.uk.screen.at.app.ksm008.query.b.DisplaySimultanAtdDesginDtl;
import nts.uk.screen.at.app.ksm008.query.b.GetInformationStartupScreenQuery;
import nts.uk.screen.at.app.ksm008.query.b.dto.InitScreenDto;
import nts.uk.screen.at.app.ksm008.query.b.dto.ParamInitScreen;
import nts.uk.screen.at.app.ksm008.query.b.dto.PersonInfoDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("screen/at/ksm008/b")
@Produces(MediaType.APPLICATION_JSON)
public class InitScreen {

    @Inject
    private GetInformationStartupScreenQuery getStartup;

    @Inject
    private DisplaySimultanAtdDesginDtl displaySimultanAtdDesginDtl;


    @POST
    @Path("init")
    public InitScreenDto init(ParamInitScreen param) {
        return getStartup.getInfoStartup(param);
    }

    @POST
    @Path("getSimultaneousDips")
    public List<PersonInfoDto> getSimultaneousDips(ParamInitScreen param) {
        String sid = param.getCode();
        return displaySimultanAtdDesginDtl.getSimultaneousAttendaceDesignDtl(sid);
    }

}
