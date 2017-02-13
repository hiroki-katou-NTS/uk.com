package nts.uk.ctx.pr.core.ws.insurance.social.healthavgearn;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.find.HealthInsuranceAvgearnDto;
import nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.find.HealthInsuranceAvgearnFinder;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnRepository;

@Path("ctx/pr/core/insurance/social/healthavgearn")
@Produces("application/json")
public class HealthInsuranceAvgearnWs extends WebService {

	@Inject
	private HealthInsuranceAvgearnFinder healthInsuranceAvgearnFinder;
	@Inject
	private HealthInsuranceAvgearnRepository healthInsuranceAvgearnRepository;

	@POST
	@Path("update")
	public void update(List<HealthInsuranceAvgearn> listHealthInsuranceAvgearn) {
		listHealthInsuranceAvgearn.forEach(item -> healthInsuranceAvgearnRepository.update(item));
	}

	@POST
	@Path("find/{id}")
	public List<HealthInsuranceAvgearnDto> find(@PathParam("id") String id) {
		return healthInsuranceAvgearnFinder.find(id);
	}
}
