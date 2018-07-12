package nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
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
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
@Transactional
public class UpdateCareLeaveCommandHandler extends CommandHandler<UpdateCareLeaveCommand>
		implements PeregUpdateCommandHandler<UpdateCareLeaveCommand> {

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
		return UpdateCareLeaveCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateCareLeaveCommand> context) {
		String cId = AppContexts.user().companyId();
		UpdateCareLeaveCommand data = context.getCommand();

		// child-care-data
		ChildCareLeaveRemainingData childCareData = ChildCareLeaveRemainingData.getChildCareHDRemaining(data.getSId(),
				data.getChildCareUsedDays() == null ? 0.0 : data.getChildCareUsedDays().doubleValue());
		Optional<ChildCareLeaveRemainingData> checkChildCareDataisPresent = childCareDataRepo
				.getChildCareByEmpId(childCareData.getSId());
		if (checkChildCareDataisPresent.isPresent()) {
			childCareDataRepo.update(childCareData, cId);
		} else {
			if (data.getChildCareUsedDays() != null)
				childCareDataRepo.add(childCareData, cId);
		}

		// care-data
		LeaveForCareData careData = LeaveForCareData.getCareHDRemaining(data.getSId(),
				data.getCareUsedDays() == null ? 0.0 : data.getCareUsedDays().doubleValue());
		Optional<LeaveForCareData> checkCareDataisPresent = careDataRepo.getCareByEmpId(careData.getSId());

		if (checkCareDataisPresent.isPresent()) {
			careDataRepo.update(careData, cId);
		} else {
			if (data.getCareUsedDays() != null)
				careDataRepo.add(careData, cId);
		}

		// child-care-info
		ChildCareLeaveRemainingInfo childCareInfo = ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(data.getSId(),
				data.getChildCareUseArt().intValue(),
				data.getChildCareUpLimSet() == null ? UpperLimitSetting.FAMILY_INFO.value
						: data.getChildCareUpLimSet().intValue(),
				data.getChildCareThisFiscal() == null ? null : data.getChildCareThisFiscal().doubleValue(),
				data.getChildCareNextFiscal() == null ? null : data.getChildCareNextFiscal().doubleValue());
		Optional<ChildCareLeaveRemainingInfo> checkChildCareInfoisPresent = childCareInfoRepo
				.getChildCareByEmpId(childCareInfo.getSId());
		if (checkChildCareInfoisPresent.isPresent()) {
			childCareInfoRepo.update(childCareInfo, cId);
		} else {
			childCareInfoRepo.add(childCareInfo, cId);
		}

		// care-info
		LeaveForCareInfo careInfo = LeaveForCareInfo.createCareLeaveInfo(data.getSId(),
				data.getCareUseArt().intValue(),
				data.getCareUpLimSet() == null ? UpperLimitSetting.FAMILY_INFO.value
						: data.getCareUpLimSet().intValue(),
				data.getCareThisFiscal() == null ? null : data.getCareThisFiscal().doubleValue(),
				data.getCareNextFiscal() == null ? null : data.getCareNextFiscal().doubleValue());
		Optional<LeaveForCareInfo> checkCareInfoisPresent = careInfoRepo.getCareByEmpId(careInfo.getSId());
		if (checkCareInfoisPresent.isPresent()) {
			careInfoRepo.update(careInfo, cId);
		} else {
			careInfoRepo.add(careInfo, cId);
		}
	}

}
