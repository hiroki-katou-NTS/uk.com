package nts.uk.screen.at.ws.kaf.kaf021;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kaf021.find.EmployeeAgreementTimeDto;
import nts.uk.screen.at.app.kaf021.find.EmployeeBasicInfoDto;
import nts.uk.screen.at.app.kaf021.find.Kaf021Finder;
import nts.uk.screen.at.app.kaf021.find.StartupInfo;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("screen/at/kaf021")
@Produces("application/json")
public class Kaf021WebService extends WebService {

    @Inject
    private Kaf021Finder kaf021Finder;

    @POST
    @Path("init")
    public StartupInfo init() {
        return this.kaf021Finder.initStarup();
    }

    @POST
    @Path("get-current-month")
    public List<EmployeeAgreementTimeDto> getCurrentMonth(EmployeeInfoParam param) {
        return this.kaf021Finder.getEmloyeeInfoForCurrentMonth(param.getEmployees());
    }

    @POST
    @Path("get-next-month")
    public List<EmployeeAgreementTimeDto> getNextMonth(EmployeeInfoParam param) {
        return this.kaf021Finder.getEmloyeeInfoForNextMonth(param.getEmployees());
    }

    @POST
    @Path("get-year")
    public List<EmployeeAgreementTimeDto> getYear(EmployeeInfoParam param) {
        return this.kaf021Finder.getEmloyeeInfoForYear(param.getEmployees());
    }
}
