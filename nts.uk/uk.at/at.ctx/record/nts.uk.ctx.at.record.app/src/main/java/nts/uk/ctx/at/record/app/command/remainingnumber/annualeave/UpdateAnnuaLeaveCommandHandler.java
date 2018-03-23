package nts.uk.ctx.at.record.app.command.remainingnumber.annualeave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateAnnuaLeaveCommandHandler extends CommandHandler<AnnuaLeaveCommand>
		implements PeregUpdateCommandHandler<AnnuaLeaveCommand> {

	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaBasicInfoRepo;

	@Inject
	private AnnLeaMaxDataRepository maxDataRepo;

	@Override
	protected void handle(CommandHandlerContext<AnnuaLeaveCommand> context) {
		AnnuaLeaveCommand c = context.getCommand();

		AnnualLeaveEmpBasicInfo basicInfo = AnnualLeaveEmpBasicInfo.createFromJavaType(c.getEmployeeId(),
				c.getWorkingDaysPerYear(), c.getWorkingDayBeforeIntro(), c.getGrantTable(), c.getStandardDate());
		annLeaBasicInfoRepo.add(basicInfo);

		AnnualLeaveMaxData maxData = AnnualLeaveMaxData.createFromJavaType(c.getEmployeeId(), c.getMaxTimes(),
				c.getUsedTimes(), c.getMaxTimes() - c.getUsedTimes(), c.getMaxMinutes(), c.getUsedMinutes(),
				c.getMaxMinutes() - c.getUsedMinutes());
		maxDataRepo.add(maxData);
	}

	@Override
	public String targetCategoryCd() {
		return "CS00024";
	}

	@Override
	public Class<?> commandClass() {
		return AnnuaLeaveCommand.class;
	}

}
