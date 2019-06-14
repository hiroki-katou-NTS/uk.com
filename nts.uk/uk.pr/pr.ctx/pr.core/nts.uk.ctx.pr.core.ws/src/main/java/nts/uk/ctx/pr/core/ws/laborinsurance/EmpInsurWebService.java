package nts.uk.ctx.pr.core.ws.laborinsurance;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.laborinsurance.accident.AccInsurHisCommand;
import nts.uk.ctx.pr.core.app.command.laborinsurance.accident.UpdateAccInsurHisCommandHandler;
import nts.uk.ctx.pr.core.app.command.laborinsurance.employment.EmpInsurHisCommand;
import nts.uk.ctx.pr.core.app.command.laborinsurance.employment.RegisterEmpInsurBusBurRatioCommand;
import nts.uk.ctx.pr.core.app.command.laborinsurance.employment.RegisterEmpInsurPreRateCommandHandler;
import nts.uk.ctx.pr.core.app.command.laborinsurance.employment.UpdateEmpInsurHisCommandHandler;
import nts.uk.ctx.pr.core.app.find.laborinsurance.EmpInsurHisDto;
import nts.uk.ctx.pr.core.app.find.laborinsurance.EmpInsurHisFinder;
import nts.uk.ctx.pr.core.app.find.laborinsurance.EmpInsurPreRateDto;
import nts.uk.ctx.pr.core.app.find.laborinsurance.EmpInsurPreRateFinder;


@Path("core/monsalabonus/laborinsur")
@Produces("application/json")
public class EmpInsurWebService extends WebService {

	@Inject
	private EmpInsurPreRateFinder empInsurPreRateFinder;
	
	@Inject
	private EmpInsurHisFinder empInsurHisFinder;
	
	@Inject
	private RegisterEmpInsurPreRateCommandHandler registerEmpInsurPreRateCommandHandler;
	
	@Inject
	private UpdateEmpInsurHisCommandHandler updateEmpInsurHisCommandHandler;

	@Inject
	private UpdateAccInsurHisCommandHandler updateAccInsurHisCommandHandler;
	
	@POST
	@Path("getEmpInsurPreRate/{hisId}")
	public List<EmpInsurPreRateDto> getEmpInsurPreRate(@PathParam("hisId") String hisId) {
		return empInsurPreRateFinder.getListEmplInsurPreRate(hisId);
	}
	
	@POST
	@Path("getEmpInsurHis")
	public List<EmpInsurHisDto> getEmpInsurHis() {
		return empInsurHisFinder.getListEmplInsurHis();
	}
	
	@POST
	@Path("register")
	public void registerEmpInsurPreRate(RegisterEmpInsurBusBurRatioCommand command){
		registerEmpInsurPreRateCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateEmpInsurHis")
	public void updateEmpInsurHis(EmpInsurHisCommand command){
		updateEmpInsurHisCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAccInsurHis")
	public void updateWorkersCompenInsur(AccInsurHisCommand command){
		updateAccInsurHisCommandHandler.handle(command);
	}

}
