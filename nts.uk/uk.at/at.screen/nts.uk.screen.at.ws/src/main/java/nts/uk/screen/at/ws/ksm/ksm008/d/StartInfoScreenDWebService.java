package nts.uk.screen.at.ws.ksm.ksm008.d;

import nts.uk.screen.at.app.ksm008.sceenD.Ksm008DStartInfoDto;
import nts.uk.screen.at.app.ksm008.sceenD.Ksm008DStartupInfoProcessor;
import nts.uk.screen.at.app.ksm008.screenE.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("screen/at/ksm008/d")
@Produces(MediaType.APPLICATION_JSON)
public class StartInfoScreenDWebService {

    @Inject
    Ksm008DStartupInfoProcessor startupInfoProcessor;

    @Inject
    GetRelationshipDetailsProcessor relationshipDetailsProcessor;

    @Inject
    GetLstRelshipsBetweenOgrWorkProcessor betweenOgrWorkProcessor;

    /**
     * 初期起動の情報取得する
     */
    @POST
    @Path("getStartupInfo")
    public Ksm008DStartInfoDto get() {
        return startupInfoProcessor.getStartupInfo("");
    }

    /**
     * 組織の勤務方法の関係性明細を表示する
     */
    @POST
    @Path("getDetails")
    public RelationshipDetailDto get(RequestPrams requestPrams) {
        return relationshipDetailsProcessor.getRelationshipDetails(requestPrams);
    }

    @POST
    @Path("getLstRelships")
    public List<RelationshipDetailDto> get(RequestRelshipPrams requestPrams) {
        return betweenOgrWorkProcessor.getLstRelshipsBetweenOgrWork(requestPrams);
    }
}
