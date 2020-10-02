package nts.uk.screen.at.ws.kaf.kaf021;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kaf021.query.a.EmployeeAgreementTimeDto;
import nts.uk.screen.at.app.kaf021.query.a.SpecialProvisionOfAgreementSelectionQuery;
import nts.uk.screen.at.app.kaf021.query.a.StartupInfo;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("screen/at/kaf021")
@Produces("application/json")
public class Kaf021WebService extends WebService {

    @Inject
    private SpecialProvisionOfAgreementSelectionQuery kaf021A_query;

    @POST
    @Path("init")
    public StartupInfo init() {
        return this.kaf021A_query.initStarup();
    }

    @POST
    @Path("get-current-month")
    public List<EmployeeAgreementTimeDto> getCurrentMonth(EmployeeInfoParam param) {
        return this.kaf021A_query.getEmloyeeInfoForCurrentMonth(param.getEmployees());
    }

    @POST
    @Path("get-next-month")
    public List<EmployeeAgreementTimeDto> getNextMonth(EmployeeInfoParam param) {
        return this.kaf021A_query.getEmloyeeInfoForNextMonth(param.getEmployees());
    }

    @POST
    @Path("get-year")
    public List<EmployeeAgreementTimeDto> getYear(EmployeeInfoParam param) {
        return this.kaf021A_query.getEmloyeeInfoForYear(param.getEmployees());
    }
}
