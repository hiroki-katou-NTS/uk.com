package nts.uk.screen.at.ws.kaf.kaf021;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kaf021.query.SpecialProvisionOfAgreementQuery;
import nts.uk.screen.at.app.kaf021.query.a.EmployeeAgreementTimeDto;
import nts.uk.screen.at.app.kaf021.query.a.StartupInfo;
import nts.uk.screen.at.app.kaf021.query.c_d.SpecialProvisionOfAgreementAppListDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * KAF021 Web service
 *
 * @author Le Huu Dat
 */
@Path("screen/at/kaf021")
@Produces("application/json")
public class Kaf021WebService extends WebService {

    @Inject
    private SpecialProvisionOfAgreementQuery query;

    @POST
    @Path("init")
    public StartupInfo init() {
        return this.query.initStarup();
    }

    @POST
    @Path("get-current-month")
    public List<EmployeeAgreementTimeDto> getCurrentMonth(EmployeeInfoParam param) {
        return this.query.getEmloyeeInfoForCurrentMonth(param.getEmployees());
    }

    @POST
    @Path("get-next-month")
    public List<EmployeeAgreementTimeDto> getNextMonth(EmployeeInfoParam param) {
        return this.query.getEmloyeeInfoForNextMonth(param.getEmployees());
    }

    @POST
    @Path("get-year")
    public List<EmployeeAgreementTimeDto> getYear(EmployeeInfoParam param) {
        return this.query.getEmloyeeInfoForYear(param.getEmployees());
    }

    @POST
    @Path("init-display")
    public SpecialProvisionOfAgreementAppListDto initDisplay(SpecialProvisionOfAgreementAppParam param) {
        return this.query.initDisplay(param.getStatus());
    }

    @POST
    @Path("search")
    public SpecialProvisionOfAgreementAppListDto search(SpecialProvisionOfAgreementAppParam param) {
        return this.query.search(param.getStartDate(), param.getEndDate(), param.getStatus());
    }

    @POST
    @Path("init-display-approve")
    public SpecialProvisionOfAgreementAppListDto initDisplayApprove(SpecialProvisionOfAgreementAppParam param) {
        return this.query.initDisplayApprove(param.getStatus());
    }

    @POST
    @Path("search-approve")
    public SpecialProvisionOfAgreementAppListDto searchApprove(SpecialProvisionOfAgreementAppParam param) {
        return this.query.searchApprove(param.getStartDate(), param.getEndDate(), param.getStatus());
    }
}
