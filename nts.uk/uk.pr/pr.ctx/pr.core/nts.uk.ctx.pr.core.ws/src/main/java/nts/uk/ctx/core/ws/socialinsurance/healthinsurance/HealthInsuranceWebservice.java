package nts.uk.ctx.core.ws.socialinsurance.healthinsurance;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.CheckHealthInsuranceGradeFeeChangeCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.DeleteHealthInsuranceCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.RegisterHealthInsuranceCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.UpdateHealthInsuranceFeeRateHistoryCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command.HealthInsuranceCommand;
import nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.HealthInsuranceFeeRateFinder;
import nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto.HealthInsuranceDto;
import nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto.SocialInsuranceOfficeDto;

@Path("ctx/core/socialinsurance/healthinsurance")
@Produces("application/json")
public class HealthInsuranceWebservice {

    @Inject
    private HealthInsuranceFeeRateFinder healthInsuranceFeeRateFinder;

    @Inject
    private RegisterHealthInsuranceCommandHandler registerHealthInsuranceCommandHandler;
    
    @Inject
    private CheckHealthInsuranceGradeFeeChangeCommandHandler checkHealthInsuranceGradeFeeChangeCommandHandler;
    
    @Inject
    private UpdateHealthInsuranceFeeRateHistoryCommandHandler updateHealthInsuranceCommandHandler;
    
    @Inject
    private DeleteHealthInsuranceCommandHandler deleteHealthInsuranceCommandHandler;

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
    @Path("register")
    public void addHealthInsuranceFeeRate(HealthInsuranceCommand healthInsuranceCommand) {
        this.registerHealthInsuranceCommandHandler.handle(healthInsuranceCommand);
    }
    
    @POST
    @Path("checkGradeFeeChange")
    public Boolean checkGradeFeeChange(HealthInsuranceCommand healthInsuranceCommand) {
        return this.checkHealthInsuranceGradeFeeChangeCommandHandler.handle(healthInsuranceCommand);
    }

    @POST
    @Path("editHistory")
    public void updateHealthInsuranceFeeRate(HealthInsuranceCommand command) {
    	updateHealthInsuranceCommandHandler.handle(command);
    }
    
    @POST
    @Path("deleteHistory")
    public void deleteHealthInsuranceFeeRate(HealthInsuranceCommand command) {
    	deleteHealthInsuranceCommandHandler.handle(command);
    }
}