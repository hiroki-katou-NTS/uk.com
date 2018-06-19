package nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.NursCareLevRemainDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.NursingCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursCareLevRemainInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
@Transactional
public class AddCareLeaveCommandHandler extends CommandHandlerWithResult<AddCareLeaveCommand, PeregAddCommandResult>
		implements PeregAddCommandHandler<AddCareLeaveCommand> {

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
		return AddCareLeaveCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddCareLeaveCommand> context) {
		String cId = AppContexts.user().companyId();
		AddCareLeaveCommand data = context.getCommand();

		if (data.getChildCareUsedDays() != null) {
			NursingCareLeaveRemainingData childCareData = NursingCareLeaveRemainingData.getChildCareHDRemaining(
					data.getSId(), data.getChildCareUsedDays().doubleValue());
			dataRepo.add(childCareData, cId);
		}

		if (data.getCareUsedDays() != null) {
			NursingCareLeaveRemainingData careData = NursingCareLeaveRemainingData.getCareHDRemaining(
					data.getSId(),data.getCareUsedDays().doubleValue());
			dataRepo.add(careData, cId);
		}
		

		NursingCareLeaveRemainingInfo childCareInfo = NursingCareLeaveRemainingInfo.createChildCareLeaveInfo(
				data.getSId(), data.getChildCareUseArt() == null ? 0 : data.getChildCareUseArt().intValue(),
				data.getChildCareUpLimSet() == null ? UpperLimitSetting.FAMILY_INFO.value
						: data.getChildCareUpLimSet().intValue(),
				data.getChildCareThisFiscal() == null ? null : data.getChildCareThisFiscal().doubleValue(),
				data.getChildCareNextFiscal() == null ? null : data.getChildCareNextFiscal().doubleValue());

		NursingCareLeaveRemainingInfo careInfo = NursingCareLeaveRemainingInfo.createCareLeaveInfo(data.getSId(),
				data.getCareUseArt() == null ? 0 : data.getCareUseArt().intValue(),
				data.getCareUpLimSet() == null ? UpperLimitSetting.FAMILY_INFO.value
						: data.getCareUpLimSet().intValue(),
				data.getCareThisFiscal() == null ? null : data.getCareThisFiscal().doubleValue(),
				data.getCareNextFiscal() == null ? null : data.getCareNextFiscal().doubleValue());
		infoRepo.add(childCareInfo, cId);
		infoRepo.add(careInfo, cId);
		return new PeregAddCommandResult(data.getSId());
	}

}
