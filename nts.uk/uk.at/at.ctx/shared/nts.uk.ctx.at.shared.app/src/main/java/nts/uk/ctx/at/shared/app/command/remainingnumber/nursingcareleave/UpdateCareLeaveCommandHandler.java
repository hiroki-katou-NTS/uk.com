package nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.NursCareLevRemainDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.NursingCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursCareLevRemainInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
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
		NursingCareLeaveRemainingData childCareData = NursingCareLeaveRemainingData.getChildCareHDRemaining(data.getSId(), data.getChildCareUsedDays()== null? 0.0: data.getChildCareUsedDays().doubleValue());
		NursingCareLeaveRemainingData careData = NursingCareLeaveRemainingData.getCareHDRemaining(data.getSId(), data.getCareUsedDays() == null? 0.0: data.getCareUsedDays().doubleValue());
		
		// check childCareData isPresent
		Optional<NursingCareLeaveRemainingData> checkChildCareDataisPresent= dataRepo.getChildCareByEmpId(childCareData.getSId());
		if (checkChildCareDataisPresent.isPresent()) {
			dataRepo.update(childCareData, cId);
		}else {
			if(data.getChildCareUsedDays() != null)
			dataRepo.add(childCareData, cId);
		}
		
		//  check careData isPresent
		Optional<NursingCareLeaveRemainingData> checkCareDataisPresent = dataRepo.getCareByEmpId(careData.getSId());
		
		if (checkCareDataisPresent.isPresent()) {
			dataRepo.update(careData, cId);
		}else {
			if(data.getCareUsedDays() != null)
			dataRepo.add(careData, cId);
		}
		
		
		NursingCareLeaveRemainingInfo childCareInfo = NursingCareLeaveRemainingInfo.createChildCareLeaveInfo(data.getSId(), data.getChildCareUseArt().intValue(), 
				data.getChildCareUpLimSet() == null? UpperLimitSetting.FAMILY_INFO.value: data.getChildCareUpLimSet().intValue(), 
				data.getChildCareThisFiscal() == null? null : data.getChildCareThisFiscal().doubleValue(), 
				data.getChildCareNextFiscal() == null? null: data.getChildCareNextFiscal().doubleValue());
		NursingCareLeaveRemainingInfo careInfo= NursingCareLeaveRemainingInfo.createCareLeaveInfo(data.getSId(), data.getCareUseArt().intValue(), 
				data.getCareUpLimSet() == null? UpperLimitSetting.FAMILY_INFO.value:data.getCareUpLimSet().intValue(), 
				data.getCareThisFiscal() == null? null: data.getCareThisFiscal().doubleValue(), 
				data.getCareNextFiscal() == null? null: data.getCareNextFiscal().doubleValue());
		
		// check childCareInfo isPresent
		Optional<NursingCareLeaveRemainingInfo> checkChildCareInfoisPresent= infoRepo.getChildCareByEmpId(childCareInfo.getSId());
		if (checkChildCareInfoisPresent.isPresent()) {
			infoRepo.update(childCareInfo, cId);
		} else {
			infoRepo.add(childCareInfo, cId);
		}
		
		// check cCareInfo isPresent
		Optional<NursingCareLeaveRemainingInfo> checkCareInfoisPresent= infoRepo.getCareByEmpId(careInfo.getSId());
		if (checkCareInfoisPresent.isPresent()) {
			infoRepo.update(careInfo, cId);
		} else {
			infoRepo.add(careInfo, cId);
		}
	}

}
