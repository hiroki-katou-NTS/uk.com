package nts.uk.screen.at.ws.kmt.kmt013;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kmt013.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("at/screen/kmt013")
@Produces(MediaType.APPLICATION_JSON)
public class OrgSupportWebService extends WebService {
    @Inject
    private SupportFuncGetOrganizationScreenQuery supportFuncGetOrganizationScreenQuery;

    @Inject
    private ListOrgCanBeSupportedScreenQuery listOrgCanBeSupportedScreenQuery;

    @Path("findAll")
    @POST
    public List<SupportFuncGetOrganizationDto> get() {
        return supportFuncGetOrganizationScreenQuery.get();
    }

    @Path("findAllById")
    @POST
    public TargetOrgInfoDto get(TargetOrgParams params) {
        return listOrgCanBeSupportedScreenQuery.get(params.getBaseDate(),params.getUnit(),params.getOrgId());
    }
}
