package nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareLeaveRemainingDataService;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
@Transactional
public class AddCareLeaveListCommandHandler extends CommandHandlerWithResult<List<AddCareLeaveCommand>, List<MyCustomizeException>>
implements PeregAddListCommandHandler<AddCareLeaveCommand>{

	@Inject
	private ChildCareLeaveRemainingDataService  service;

	@Override
	public String targetCategoryCd() {
		return "CS00036";
	}

	@Override
	public Class<?> commandClass() {
		return AddCareLeaveCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddCareLeaveCommand>> context) {
		String cid = AppContexts.user().companyId();
		List<AddCareLeaveCommand> cmd = context.getCommand();
		List<PeregAddCommandResult> result = new ArrayList<>();
		List<ChildCareUsedNumberData> childCareDataInsert = new ArrayList<>();
		List<CareUsedNumberData> leaveCareDataInsert = new ArrayList<>();
		List<ChildCareLeaveRemainingInfo> childCareLeaveInfoInsert = new ArrayList<>();
		List<CareLeaveRemainingInfo> leaveCareInfoInsert = new ArrayList<>();
		cmd.stream().forEach(c ->{
			// 子の看護-使用数
			if (c.getChildCareUsedDays() != null) {
				ChildCareUsedNumberData usedNumber = new ChildCareUsedNumberData(
						c.getSId(),
						new ChildCareNurseUsedNumber(
								new DayNumberOfUse(c.getChildCareUsedDays().doubleValue())
								, c.getChildCareUsedTimes() == null ? Optional.empty() : Optional.of(new TimeOfUse(c.getChildCareUsedTimes().intValue()))
						)
				);
				childCareDataInsert.add(usedNumber);
			}

			// 介護-使用数
			if (c.getCareUsedDays() != null) {
				CareUsedNumberData usedNumber = new CareUsedNumberData(
						c.getSId(),
						new ChildCareNurseUsedNumber(
								new DayNumberOfUse(c.getCareUsedDays().doubleValue())
								, c.getCareUsedDays() == null ? Optional.empty() : Optional.of(new TimeOfUse(c.getCareUsedDays().intValue()))
						)
				);
				leaveCareDataInsert.add(usedNumber);
			}

			// 子の看護 - 上限情報
			ChildCareLeaveRemainingInfo childCareInfo = ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(
					c.getSId(), c.getChildCareUseArt() == null ? 0 : c.getChildCareUseArt().intValue(),
					c.getChildCareUpLimSet() == null ? UpperLimitSetting.PER_INFO_EVERY_YEAR.value
							: c.getChildCareUpLimSet().intValue(),
					c.getChildCareThisFiscal() == null ? 0 : c.getChildCareThisFiscal().intValue(),
					c.getChildCareNextFiscal() == null ? 0 : c.getChildCareNextFiscal().intValue());
			if (c.getChildCareUsedDays() != null) {
				childCareLeaveInfoInsert.add(childCareInfo);
			}

			// 介護-上限情報
			CareLeaveRemainingInfo careInfo = CareLeaveRemainingInfo.createCareLeaveInfo(c.getSId(),
					c.getCareUseArt() == null ? 0 : c.getCareUseArt().intValue(),
					c.getCareUpLimSet() == null ? UpperLimitSetting.PER_INFO_EVERY_YEAR.value
							: c.getCareUpLimSet().intValue(),
					c.getCareThisFiscal() == null ? 0 : c.getCareThisFiscal().intValue(),
					c.getCareNextFiscal() == null ? 0 : c.getCareNextFiscal().intValue());
			if (c.getCareUsedDays() != null) {
				leaveCareInfoInsert.add(careInfo);
			}
			result.add(new PeregAddCommandResult(c.getSId()));
		});
		service.addData(cid, childCareDataInsert, leaveCareDataInsert, childCareLeaveInfoInsert, leaveCareInfoInsert);
		return new ArrayList<>();
	}

}
