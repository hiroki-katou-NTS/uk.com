package nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave;


import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
@Transactional
public class AddCareLeaveCommandHandler extends CommandHandlerWithResult<AddCareLeaveCommand, PeregAddCommandResult>
		implements PeregAddCommandHandler<AddCareLeaveCommand> {

	@Inject
	private CareLeaveRemainingInfoRepository careInfoRepo;

	@Inject
	private ChildCareLeaveRemInfoRepository childCareInfoRepo;

	@Inject
	private ChildCareUsedNumberRepository childCareUsedNumberRepository;

	@Inject
	private CareUsedNumberRepository careUsedNumberRepository;

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
		// 子の看護-使用数
		ChildCareUsedNumberData childUsedNumber = new ChildCareUsedNumberData(
				data.getSId(),
				new ChildCareNurseUsedNumber(
						data.getChildCareUsedDays() == null ? new DayNumberOfUse(Double.valueOf(0)) : new DayNumberOfUse(data.getChildCareUsedDays().doubleValue())
						, data.getChildCareUsedTimes() == null ? Optional.of(new TimeOfUse(0)) : Optional.of(new TimeOfUse(data.getChildCareUsedTimes().intValue()))
				)
		);
		childCareUsedNumberRepository.add(cId, childUsedNumber);
		

		// 介護-使用数
		CareUsedNumberData usedNumber = new CareUsedNumberData(
				data.getSId(),
				new ChildCareNurseUsedNumber(
						data.getCareUsedDays() == null ? new DayNumberOfUse(Double.valueOf(0)) : new DayNumberOfUse(data.getCareUsedDays().doubleValue())
						, data.getCareUsedTimes() == null ? Optional.of(new TimeOfUse(0)) : Optional.of(new TimeOfUse(data.getCareUsedTimes().intValue()))
				)
		);
		careUsedNumberRepository.add(cId, usedNumber);

		// 子の看護 - 上限情報
		ChildCareLeaveRemainingInfo childCareInfo = ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(
				data.getSId(), data.getChildCareUseArt() == null ? 0 : data.getChildCareUseArt().intValue(),
				data.getChildCareUpLimSet() == null ? UpperLimitSetting.PER_INFO_EVERY_YEAR.value
						: data.getChildCareUpLimSet().intValue(),
				data.getChildCareThisFiscal() == null ? 0 : data.getChildCareThisFiscal().intValue(),
				data.getChildCareNextFiscal() == null ? 0 : data.getChildCareNextFiscal().intValue());
		childCareInfoRepo.add(childCareInfo, cId);
		

		// 介護-上限情報
		CareLeaveRemainingInfo careInfo = CareLeaveRemainingInfo.createCareLeaveInfo(data.getSId(),
				data.getCareUseArt() == null ? 0 : data.getCareUseArt().intValue(),
				data.getCareUpLimSet() == null ? UpperLimitSetting.PER_INFO_EVERY_YEAR.value
						: data.getCareUpLimSet().intValue(),
				data.getCareThisFiscal() == null ? 0 : data.getCareThisFiscal().intValue(),
				data.getCareNextFiscal() == null ? 0 : data.getCareNextFiscal().intValue());
		careInfoRepo.add(careInfo, cId);

		return new PeregAddCommandResult(data.getSId());
	}

}
