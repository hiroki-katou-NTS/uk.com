package nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemaiDataRepo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareDataRepo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
@Transactional
public class AddCareLeaveCommandHandler extends CommandHandlerWithResult<AddCareLeaveCommand, PeregAddCommandResult>
		implements PeregAddCommandHandler<AddCareLeaveCommand> {

	@Inject
	private LeaveForCareInfoRepository careInfoRepo;

	@Inject
	private ChildCareLeaveRemInfoRepository childCareInfoRepo;

	@Inject
	private ChildCareLeaveRemaiDataRepo childCareDataRepo;
	
	@Inject
	private LeaveForCareDataRepo careDataRepo;

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
		// child-care-data
		if (data.getChildCareUsedDays() != null) {
			ChildCareLeaveRemainingData childCareData = ChildCareLeaveRemainingData.getChildCareHDRemaining(
					data.getSId(), data.getChildCareUsedDays().doubleValue());
			childCareDataRepo.add(childCareData, cId);
		}

		// care-data
		if (data.getCareUsedDays() != null) {
			LeaveForCareData careData = LeaveForCareData.getCareHDRemaining(
					data.getSId(),data.getCareUsedDays().doubleValue());
			careDataRepo.add(careData, cId);
		}

		ChildCareLeaveRemainingInfo childCareInfo = ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(
				data.getSId(), data.getChildCareUseArt() == null ? 0 : data.getChildCareUseArt().intValue(),
				data.getChildCareUpLimSet() == null ? UpperLimitSetting.FAMILY_INFO.value
						: data.getChildCareUpLimSet().intValue(),
				data.getChildCareThisFiscal() == null ? null : data.getChildCareThisFiscal().doubleValue(),
				data.getChildCareNextFiscal() == null ? null : data.getChildCareNextFiscal().doubleValue());
		childCareInfoRepo.add(childCareInfo, cId);

		LeaveForCareInfo careInfo = LeaveForCareInfo.createCareLeaveInfo(data.getSId(),
				data.getCareUseArt() == null ? 0 : data.getCareUseArt().intValue(),
				data.getCareUpLimSet() == null ? UpperLimitSetting.FAMILY_INFO.value
						: data.getCareUpLimSet().intValue(),
				data.getCareThisFiscal() == null ? null : data.getCareThisFiscal().doubleValue(),
				data.getCareNextFiscal() == null ? null : data.getCareNextFiscal().doubleValue());
		careInfoRepo.add(careInfo, cId);
		
		return new PeregAddCommandResult(data.getSId());
	}

}
