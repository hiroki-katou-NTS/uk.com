package nts.uk.ctx.pr.core.ws.insurance.social.pensionrate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.DeletePensionCommand;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.DeletePensionCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.RegisterPensionCommand;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.RegisterPensionCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.UpdatePensionCommand;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.UpdatePensionCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionOfficeItemDto;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionRateDto;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionRateFinder;
import nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryBaseService;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.PensionRateService;
import nts.uk.ctx.pr.core.ws.base.simplehistory.SimpleHistoryWs;
import nts.uk.ctx.pr.core.ws.base.simplehistory.dto.HistoryModel;
import nts.uk.ctx.pr.core.ws.base.simplehistory.dto.MasterModel;
import nts.uk.shr.com.context.AppContexts;

@Path("ctx/pr/core/insurance/social/pensionrate")
@Produces("application/json")
public class PensionRateWs extends SimpleHistoryWs<SocialInsuranceOffice, PensionRate> {

	@Inject
	private PensionRateFinder pensionRateFinder;
	@Inject
	private RegisterPensionCommandHandler registerPensionCommandHandler;
	@Inject
	private UpdatePensionCommandHandler updatePensionCommandHandler;
	@Inject
	private DeletePensionCommandHandler deletePensionCommandHandler;
	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;
	@Inject
	private PensionRateRepository pensionRateRepository;
	@Inject
	private PensionRateService service;
	@POST
	@Path("find/{id}")
	public PensionRateDto find(@PathParam("id") String id) {
		return pensionRateFinder.find(id).get();
	}
	
	@POST
	@Path("findAllHistory")
	public List<PensionOfficeItemDto> findbyCode() {
		return pensionRateFinder.findAllHistory();
	}
	
	@POST
	@Path("create")
	public void create(RegisterPensionCommand command) {
		registerPensionCommandHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(UpdatePensionCommand command) {
		updatePensionCommandHandler.handle(command);
	}
	
	@POST
	@Path("remove")
	public void remove(DeletePensionCommand command) {
		deletePensionCommandHandler.handle(command);
	}
	
	@POST
	@Path("masterhistory")
	@Override
	public List<MasterModel> loadMasterModelList() {
		// Get list of Office.
				List<SocialInsuranceOffice> socialInsuranceOffices = this.socialInsuranceOfficeRepository
						.findAll(new CompanyCode(AppContexts.user().companyCode()));

				// Get list of unit price history.
				List<PensionRate> pensionRates = this.pensionRateRepository
						.findAll(new CompanyCode(AppContexts.user().companyCode()));

				// Group histories by unit code.
				Map<OfficeCode, List<HistoryModel>> historyMap = pensionRates.stream()
						.collect(Collectors.groupingBy(PensionRate::getOfficeCode, Collectors.mapping((res) -> {
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
	protected SimpleHistoryBaseService<SocialInsuranceOffice, PensionRate> getServices() {
		return this.service;
	}
}
