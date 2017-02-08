package nts.uk.ctx.pr.core.ws.insurance.social;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import nts.uk.ctx.pr.core.app.command.insurance.social.health.RegisterHealthInsuranceCommand;
import nts.uk.ctx.pr.core.app.command.insurance.social.health.RegisterHealthInsuranceCommandHandler;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.HealthInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;

public class HealthInsuranceService {
	@Inject
	private RegisterHealthInsuranceCommandHandler registerHealthInsuranceCommandHandler;
	
	// Find all HealthInsuranceRateDto conection data
		@POST
		@Path("findall")
		public List<HealthInsuranceRateDto> findAll() {
			List<HealthInsuranceRateDto> HealthInsuranceRateDtoList = new ArrayList<HealthInsuranceRateDto>();
			
//			HealthInsuranceRateDto HealthInsuranceRateDto001 = new HealthInsuranceRateDto();
//			HealthInsuranceRateDto001.setOfficeCode("000000000001");
//			HealthInsuranceRateDto001.setAutoCalculate(true);
//			HealthInsuranceRateDtoList.add(HealthInsuranceRateDto001);
			
			return HealthInsuranceRateDtoList;
		}
		
		@POST
		@Path("health/list")
		public void listHealthItem(RegisterHealthInsuranceCommand command)
		{
			this.registerHealthInsuranceCommandHandler.handle(command);
			return;
		}
		
}
