package nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
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
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
@Transactional
public class UpdateCareLeaveCommandHandler extends CommandHandler<UpdateCareLeaveCommand>
		implements PeregUpdateCommandHandler<UpdateCareLeaveCommand> {

	@Inject
	private CareLeaveRemainingInfoRepository careInfoRepo;

	@Inject
	private ChildCareLeaveRemInfoRepository childCareInfoRepo;

	@Inject
	private ChildCareUsedNumberRepository childCareDataRepo;

	@Inject
	private CareUsedNumberRepository careDataRepo;

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

		// 子の看護-使用数
		ChildCareUsedNumberData usedNumber = new ChildCareUsedNumberData(
				data.getSId(),
				new ChildCareNurseUsedNumber(
						data.getChildCareUsedDays() == null ? new DayNumberOfUse(Double.valueOf(0)) : new DayNumberOfUse(data.getChildCareUsedDays().doubleValue())
						, data.getChildCareUsedTimes() == null ? Optional.of(new TimeOfUse(0)) : Optional.of(new TimeOfUse(data.getChildCareUsedTimes().intValue()))
				)
		);

		Optional<ChildCareUsedNumberData> checkChildCareDataisPresent = childCareDataRepo
				.find(data.getSId());
		if (checkChildCareDataisPresent.isPresent()) {
			childCareDataRepo.update(cId, usedNumber);
		} else {
			childCareDataRepo.add(cId, usedNumber );
		}

		// 介護-使用数
		CareUsedNumberData careUsedNumber = new CareUsedNumberData(
				data.getSId(),
				new ChildCareNurseUsedNumber(
						data.getCareUsedDays() == null ? new DayNumberOfUse(Double.valueOf(0)) : new DayNumberOfUse(data.getCareUsedDays().doubleValue())
						, data.getCareUsedTimes() == null ? Optional.of(new TimeOfUse(0)) : Optional.of(new TimeOfUse(data.getCareUsedTimes().intValue()))
				)
		);

		Optional<CareUsedNumberData> checkCareDataisPresent = careDataRepo
				.find(data.getSId());
		if (checkCareDataisPresent.isPresent()) {
			careDataRepo.update(cId, careUsedNumber);
		} else {
			careDataRepo.add(cId, careUsedNumber );
		}

		// 子の看護 - 上限情報
		ChildCareLeaveRemainingInfo childCareInfo = ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(data.getSId(),
				data.getChildCareUseArt().intValue(),
				data.getChildCareUpLimSet() == null ? UpperLimitSetting.PER_INFO_EVERY_YEAR.value
						: data.getChildCareUpLimSet().intValue(),
				data.getChildCareThisFiscal() == null ? 0 : data.getChildCareThisFiscal().intValue(),
				data.getChildCareNextFiscal() == null ? 0 : data.getChildCareNextFiscal().intValue());
		Optional<ChildCareLeaveRemainingInfo> checkChildCareInfoisPresent = childCareInfoRepo
				.getChildCareByEmpId(childCareInfo.getSId());
		if (checkChildCareInfoisPresent.isPresent()) {
			childCareInfoRepo.update(childCareInfo, cId);
		} else {
			childCareInfoRepo.add(childCareInfo, cId);
		}

		// 介護-上限情報
		CareLeaveRemainingInfo careInfo = CareLeaveRemainingInfo.createCareLeaveInfo(data.getSId(),
				data.getCareUseArt() == null ? 0 : data.getCareUseArt().intValue(),
				data.getCareUpLimSet() == null ? UpperLimitSetting.PER_INFO_EVERY_YEAR.value
						: data.getCareUpLimSet().intValue(),
				data.getCareThisFiscal() == null ? 0 : data.getCareThisFiscal().intValue(),
				data.getCareNextFiscal() == null ? 0 : data.getCareNextFiscal().intValue());

		Optional<CareLeaveRemainingInfo> checkCareInfoisPresent = careInfoRepo.getCareByEmpId(data.getSId());
		if (checkCareInfoisPresent.isPresent()) {
			careInfoRepo.update(careInfo, cId);
		} else {
			careInfoRepo.add(careInfo, cId);
		}
	}

}
