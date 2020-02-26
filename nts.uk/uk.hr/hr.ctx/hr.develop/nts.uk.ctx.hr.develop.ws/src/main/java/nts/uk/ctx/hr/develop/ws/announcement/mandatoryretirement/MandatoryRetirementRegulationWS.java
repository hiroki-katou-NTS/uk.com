package nts.uk.ctx.hr.develop.ws.announcement.mandatoryretirement;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.command.MandatoryRetirementRegulationCommand;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.HistoryIdParam;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.MandatoryRetirementRegulationDto;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.ParamAddManRetireRegDto;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.RelateMasterDto;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.find.MandatoryRetirementRegulationFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("mandatoryRetirementRegulation")
@Produces(MediaType.APPLICATION_JSON)
public class MandatoryRetirementRegulationWS {

	@Inject
	private MandatoryRetirementRegulationFinder finder;
	
	@Inject
	private MandatoryRetirementRegulationCommand command;
	
	@POST
	@Path("/getRelateMaster")
	public RelateMasterDto getRelateMaster(){
		String contractCd = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		//・共通マスタID = M000031 // CommonMasterID = M000031
		String commonMasterId = "M000031";
		Optional<RelateMasterDto> result = finder.getRelateMaster(contractCd, companyId, commonMasterId);
		if(!result.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_16");
		}
		return result.get();
	}
	
	@POST
	@Path("/get")
	public MandatoryRetirementRegulationDto getMandatoryRetirementRegulation(HistoryIdParam pram){
		String companyId = AppContexts.user().companyId();
		Optional<MandatoryRetirementRegulationDto> result = finder.getMandatoryRetirementRegulation(companyId, pram.getHistoryId());
		if(!result.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_17");
		}
		return result.get();
	}
	
	@POST
	@Path("/add")
	public void add(ParamAddManRetireRegDto param){
		command.add(param);
	}
	
	@POST
	@Path("/update")
	public void update(MandatoryRetirementRegulationDto param){
		command.update(param);
	}
}
