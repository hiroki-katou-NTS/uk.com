package nts.uk.screen.at.ws.ksm.ksm008.d;

import nts.uk.screen.at.app.ksm008.company.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("screen/at/ksm008/d")
@Produces(MediaType.APPLICATION_JSON)
public class StartInfoScreenDWebService {

    @Inject
    Ksm008DStartupInfoProcessor startupInfoProcessor;

    @Inject
    GetDetailsProcessor getDetailsProcessor;

    /**
     * 初期起動の情報取得する
     */
    @POST
    @Path("getStartupInfo")
    public Ksm008DStartInfoDto get(StartInfoPrams startInfoPrams) {
        return startupInfoProcessor.getStartupInfo(startInfoPrams);
    }

    /**
     * 組織の勤務方法の関係性明細を表示する
     */
    @POST
    @Path("getDetails")
    public DetailDto get(RequestDetailPrams requestPrams) {
        return getDetailsProcessor.getDetails(requestPrams);
    }

}
