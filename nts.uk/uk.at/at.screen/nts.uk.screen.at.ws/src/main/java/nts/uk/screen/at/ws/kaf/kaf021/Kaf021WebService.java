package nts.uk.screen.at.ws.kaf.kaf021;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.kaf021.query.a.EmployeeAgreementTimeDto;
import nts.uk.screen.at.app.kaf021.query.a.SpecialProvisionOfAgreementSelectionQuery;
import nts.uk.screen.at.app.kaf021.query.a.StartupInfo;
import nts.uk.screen.at.app.kaf021.query.c_d.SpecialProvisionOfAgreementAppListDto;
import nts.uk.screen.at.app.kaf021.query.c_d.SpecialProvisionOfAgreementAppListQuery;

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
    private SpecialProvisionOfAgreementSelectionQuery kaf021A_query;
    @Inject
    private SpecialProvisionOfAgreementAppListQuery kaf021CD_query;

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

    @POST
    @Path("init-display")
    public SpecialProvisionOfAgreementAppListDto initDisplay(List<Integer> status) {
        return this.kaf021CD_query.initDisplay(status);
    }

    @POST
    @Path("search")
    public SpecialProvisionOfAgreementAppListDto search(GeneralDate startDate, GeneralDate endDate, List<Integer> status) {
        return this.kaf021CD_query.search(startDate, endDate, status);
    }
}
