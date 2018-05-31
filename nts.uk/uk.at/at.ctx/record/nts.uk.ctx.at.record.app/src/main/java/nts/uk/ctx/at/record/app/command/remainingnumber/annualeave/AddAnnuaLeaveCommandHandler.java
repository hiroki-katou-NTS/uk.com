package nts.uk.ctx.at.record.app.command.remainingnumber.annualeave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddAnnuaLeaveCommandHandler extends CommandHandlerWithResult<AddAnnuaLeaveCommand, PeregAddCommandResult>
		implements PeregAddCommandHandler<AddAnnuaLeaveCommand> {

	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaBasicInfoRepo;

	@Inject
	private AnnLeaMaxDataRepository maxDataRepo;

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddAnnuaLeaveCommand> context) {
		AddAnnuaLeaveCommand c = context.getCommand();

		AnnualLeaveEmpBasicInfo basicInfo = AnnualLeaveEmpBasicInfo.createFromJavaType(c.getEmployeeId(),
				c.getWorkingDaysPerYear(), c.getWorkingDayBeforeIntro(), c.getGrantTable(), c.getStandardDate());
		annLeaBasicInfoRepo.add(basicInfo);

		AnnualLeaveMaxData maxData = AnnualLeaveMaxData.createFromJavaType(c.getEmployeeId(), c.getMaxTimes(),
				c.getUsedTimes(), c.getMaxMinutes(), c.getUsedMinutes());

		maxDataRepo.add(maxData);

		return new PeregAddCommandResult(c.getEmployeeId());
	}

	@Override
	public String targetCategoryCd() {
		return "CS00024";
	}

	@Override
	public Class<?> commandClass() {
		return AddAnnuaLeaveCommand.class;
	}

}
