package nts.uk.ctx.core.ws.socialinsurance.healthinsurance;

import nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.AddHealthInsuranceCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command.AddHealthInsuranceCommand;
import nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.HealthInsuranceFeeRateFinder;
import nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto.HealthInsuranceDto;
import nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto.SocialInsuranceOfficeDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx/pr/core/socialinsurance/healthinsurance")
@Produces("application/json")
public class HealthInsuranceWebservice {

    @Inject
    private HealthInsuranceFeeRateFinder healthInsuranceFeeRateFinder;

    @Inject
    private AddHealthInsuranceCommandHandler addHealthInsuranceCommandHandler;

    @POST
    @Path("getByCompanyId")
    public List<SocialInsuranceOfficeDto> getHealthInsuranceFeeRateByCompanyId() {
        return healthInsuranceFeeRateFinder.getHealthInsuranceFeeRateByCompanyId();
    }

    @POST
    @Path("getByHistoryId/{historyId}")
    public HealthInsuranceDto getHealthInsuranceByHistoryId(@PathParam("historyId") String historyId) {
        return healthInsuranceFeeRateFinder.getHealthInsuranceByHistoryId(historyId);
    }

    @POST
    @Path("add")
    public void addHealthInsuranceFeeRateByCompanyId(AddHealthInsuranceCommand addHealthInsuranceCommand) {
        this.addHealthInsuranceCommandHandler.handle(addHealthInsuranceCommand);
    }

    @POST
    @Path("update")
    public void updateHealthInsuranceFeeRateByCompanyId() {
    }
}