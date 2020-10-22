package nts.uk.screen.at.ws.ksm.ksm008.c;

import nts.uk.screen.at.app.ksm008.query.b.dto.ParamInitScreen;
import nts.uk.screen.at.app.ksm008.query.b.dto.PersonInfoDto;
import nts.uk.screen.at.app.ksm008.query.c.GetEmployeeOrganizationInfoScreenQuery;
import nts.uk.screen.at.app.ksm008.query.c.GetInformationStartupCScreenQuery;
import nts.uk.screen.at.app.ksm008.query.c.dto.GetEmployeeInfoByWorkplaceDto;
import nts.uk.screen.at.app.ksm008.query.c.dto.InitScreenCDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("screen/at/ksm008/c")
@Produces(MediaType.APPLICATION_JSON)
public class InitScreen {

    @Inject
    private GetInformationStartupCScreenQuery initScreenCDto;

    @Inject
    private GetEmployeeOrganizationInfoScreenQuery getEmployeeOrganizationInfoScreenQuery;

    // ①<<ScreenQuery>> 初期起動の情報取得する
    @POST
    @Path("init")
    public InitScreenCDto init(ParamInitScreen param) {
        String code = param.getCode();
        return this.initScreenCDto.get(code);
    }

    // ②<<ScreenQuery>> 組織の社員情報を取得する
    @POST
    @Path("getEmployeeInfo")
    public List<PersonInfoDto> getEmployeeInfo(GetEmployeeInfoByWorkplaceDto param) {
        int unit = param.getUnit();
        String workplaceId = param.getWorkplaceId();
        String workplaceGroupId = param.getWorkplaceGroupId();
        return getEmployeeOrganizationInfoScreenQuery.get(unit, workplaceId, workplaceGroupId);
    }

}
