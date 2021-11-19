package nts.uk.screen.at.ws.ksu.ksu008;

import nts.uk.screen.at.app.ksu008.a.GetOutPutLayoutListInfoForKsu008;
import nts.uk.screen.at.app.ksu008.a.dto.Form9LayoutDto;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author rafiqul.islam
 */

@Path("screen/at/ksu008/a")
@Produces(MediaType.APPLICATION_JSON)
public class Ksu008AWebServive {

    @Inject
    private GetOutPutLayoutListInfoForKsu008 getOutPutlayoutListInfoForKsu008;

    @POST
    @Path("get-used-info")
    public List<Form9LayoutDto> getInitInformation() {
        return this.getOutPutlayoutListInfoForKsu008.get(AppContexts.user().companyId());
    }

}
