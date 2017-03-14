package nts.uk.ctx.pr.core.ws.insurance.social.healthrate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.command.DeleteHealthInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.command.DeleteHealthInsuranceCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.command.RegisterHealthInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.command.RegisterHealthInsuranceCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.command.UpdateHealthInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.command.UpdateHealthInsuranceCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.HealthInsuranceOfficeItemDto;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.HealthInsuranceRateDto;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.HealthInsuranceRateFinder;
import nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryBaseService;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.HealthInsuranceRateService;
import nts.uk.ctx.pr.core.ws.base.simplehistory.SimpleHistoryWs;
import nts.uk.ctx.pr.core.ws.base.simplehistory.dto.HistoryModel;
import nts.uk.ctx.pr.core.ws.base.simplehistory.dto.MasterModel;
import nts.uk.shr.com.context.AppContexts;

@Path("ctx/pr/core/insurance/social/healthrate")
@Produces("application/json")
public class HealthInsuranceRateWs extends SimpleHistoryWs<SocialInsuranceOffice, HealthInsuranceRate> {

	@Inject
	private HealthInsuranceRateFinder healthInsuranceRateFinder;
	@Inject
	private RegisterHealthInsuranceCommandHandler registerHealthInsuranceCommandHandler;
	@Inject
	private UpdateHealthInsuranceCommandHandler updateHealthInsuranceCommandHandler;
	@Inject
	private DeleteHealthInsuranceCommandHandler deleteHealthInsuranceCommandHandler;
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	@Inject
	private HealthInsuranceRateRepository healthInsuranceRateRepository;
	@Inject
	private HealthInsuranceRateService service;

	// find by historyId
	@POST
	@Path("find/{id}")
	public HealthInsuranceRateDto find(@PathParam("id") String id) {
		return healthInsuranceRateFinder.find(id).get();
	}

	// find All by OfficeCode
	@POST
	@Path("findAllHistory")
	public List<HealthInsuranceOfficeItemDto> findbyCode() {
		return healthInsuranceRateFinder.findAllHistory();
	}

	@POST
	@Path("create")
	public void create(RegisterHealthInsuranceCommand command) {
		registerHealthInsuranceCommandHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(UpdateHealthInsuranceCommand command) {
		updateHealthInsuranceCommandHandler.handle(command);
	}

	// remove by historyId
	@POST
	@Path("remove")
	public void remove(DeleteHealthInsuranceCommand command) {
		deleteHealthInsuranceCommandHandler.handle(command);
	}

	@Override
	@Path("masterhistory")
	@POST
	public List<MasterModel> loadMasterModelList() {
		// Get list of Office.
		List<SocialInsuranceOffice> socialInsuranceOffices = this.socialInsuranceOfficeRepository
				.findAll(new CompanyCode(AppContexts.user().companyCode()));

		// Get list of unit price history.
		List<HealthInsuranceRate> healthInsuranceRates = this.healthInsuranceRateRepository
				.findAll(AppContexts.user().companyCode());

		// Group histories by unit code.
		Map<OfficeCode, List<HistoryModel>> historyMap = healthInsuranceRates.stream()
				.collect(Collectors.groupingBy(HealthInsuranceRate::getOfficeCode, Collectors.mapping((res) -> {
					return HistoryModel.builder().uuid(res.getUuid()).start(res.getStart().v()).end(res.getEnd().v())
							.build();
				}, Collectors.toList())));

		// Return
		return socialInsuranceOffices.stream().map(item -> {
			return MasterModel.builder().code(item.getCode().v()).name(item.getName().v())
					.historyList(historyMap.get(item.getCode())).build();
		}).collect(Collectors.toList());
	}

	@Override
	protected SimpleHistoryBaseService<SocialInsuranceOffice, HealthInsuranceRate> getServices() {
		return this.service;
	}
}
