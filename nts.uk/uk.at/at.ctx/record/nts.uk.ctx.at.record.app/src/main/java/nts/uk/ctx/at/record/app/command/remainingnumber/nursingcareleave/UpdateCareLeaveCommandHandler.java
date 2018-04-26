package nts.uk.ctx.at.record.app.command.remainingnumber.nursingcareleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data.NursCareLevRemainDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data.NursingCareLeaveRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursCareLevRemainInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
@Transactional
public class UpdateCareLeaveCommandHandler  extends CommandHandler<UpdateCareLeaveCommand>
implements PeregUpdateCommandHandler<UpdateCareLeaveCommand>{

	
	@Inject
	private NursCareLevRemainDataRepository dataRepo;
	
	@Inject
	private NursCareLevRemainInfoRepository infoRepo;
	
	@Override
	public String targetCategoryCd() {
		return "CS00036";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateCareLeaveCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateCareLeaveCommand> context) {
		String cId = AppContexts.user().companyId();
		UpdateCareLeaveCommand data = context.getCommand();
		NursingCareLeaveRemainingData childCareData = NursingCareLeaveRemainingData.getChildCareHDRemaining(data.getSId(), data.getChildCareUsedDays().intValue());
		NursingCareLeaveRemainingData careData = NursingCareLeaveRemainingData.getCareHDRemaining(data.getSId(), data.getCareUsedDays().intValue());
		dataRepo.update(childCareData, cId);
		dataRepo.update(careData, cId);
		
		NursingCareLeaveRemainingInfo childCareInfo = NursingCareLeaveRemainingInfo.createChildCareLeaveInfo(data.getSId(), data.getChildCareUseArt().intValue(), 
				data.getChildCareUpLimSet().intValue(), 
				data.getChildCareThisFiscal() == null ? Optional.empty() : Optional.of(data.getChildCareThisFiscal().intValue()), 
				data.getChildCareNextFiscal() == null ? Optional.empty() : Optional.of(data.getChildCareNextFiscal().intValue()));
		NursingCareLeaveRemainingInfo careInfo= NursingCareLeaveRemainingInfo.createCareLeaveInfo(data.getSId(), data.getCareUseArt().intValue(), 
				data.getCareUpLimSet().intValue(), 
				data.getCareThisFiscal() == null ? Optional.empty() : Optional.of(data.getCareThisFiscal().intValue()), 
				data.getCareNextFiscal() == null ? Optional.empty() : Optional.of(data.getCareNextFiscal().intValue()));
		infoRepo.update(childCareInfo, cId);
		infoRepo.update(careInfo, cId);
		
	}

}
