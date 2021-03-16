package nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareLeaveRemainingDataService;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
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
		List<ChildCareLeaveRemainingData> childCareDataInsert = new ArrayList<>();
		List<LeaveForCareData> leaveCareDataInsert = new ArrayList<>();
		List<ChildCareLeaveRemainingInfo> childCareLeaveInfoInsert = new ArrayList<>();
		List<CareLeaveRemainingInfo> leaveCareInfoInsert = new ArrayList<>();
		cmd.stream().forEach(c ->{
			// child-care-data
			if (c.getChildCareUsedDays() != null) {
				ChildCareLeaveRemainingData childCareData = ChildCareLeaveRemainingData.getChildCareHDRemaining(
						c.getSId(), c.getChildCareUsedDays().doubleValue());
				childCareDataInsert.add(childCareData);
			}

			// care-data
			if (c.getCareUsedDays() != null) {
				LeaveForCareData careData = LeaveForCareData.getCareHDRemaining(
						c.getSId(),c.getCareUsedDays().doubleValue());
				leaveCareDataInsert.add(careData);
			}

			ChildCareLeaveRemainingInfo childCareInfo = ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(
					c.getSId(), c.getChildCareUseArt() == null ? 0 : c.getChildCareUseArt().intValue(),
					c.getChildCareUpLimSet() == null ? UpperLimitSetting.FAMILY_INFO.value
							: c.getChildCareUpLimSet().intValue(),
					c.getChildCareThisFiscal() == null ? null : c.getChildCareThisFiscal().doubleValue(),
					c.getChildCareNextFiscal() == null ? null : c.getChildCareNextFiscal().doubleValue());
			childCareLeaveInfoInsert.add(childCareInfo);

			CareLeaveRemainingInfo careInfo = CareLeaveRemainingInfo.createCareLeaveInfo(c.getSId(),
					c.getCareUseArt() == null ? 0 : c.getCareUseArt().intValue(),
					c.getCareUpLimSet() == null ? UpperLimitSetting.FAMILY_INFO.value
							: c.getCareUpLimSet().intValue(),
					c.getCareThisFiscal() == null ? null : c.getCareThisFiscal().doubleValue(),
					c.getCareNextFiscal() == null ? null : c.getCareNextFiscal().doubleValue());
			leaveCareInfoInsert.add(careInfo);
			result.add(new PeregAddCommandResult(c.getSId()));
		});
		service.addData(cid, childCareDataInsert, leaveCareDataInsert, childCareLeaveInfoInsert, leaveCareInfoInsert);
		return new ArrayList<>();
	}

}
