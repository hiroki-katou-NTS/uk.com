package nts.uk.ctx.at.shared.app.command.remainingnumber.annualeave;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddAnnuaLeaveListCommandHandler extends CommandHandlerWithResult<List<AddAnnuaLeaveCommand>, List<PeregAddCommandResult>>
implements PeregAddListCommandHandler<AddAnnuaLeaveCommand>{
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaBasicInfoRepo;

	@Inject
	private AnnLeaMaxDataRepository maxDataRepo;
	@Override
	public String targetCategoryCd() {
		return "CS00024";
	}

	@Override
	public Class<?> commandClass() {
		return AddAnnuaLeaveCommand.class;
	}

	@Override
	protected List<PeregAddCommandResult> handle(CommandHandlerContext<List<AddAnnuaLeaveCommand>> context) {
		List<AddAnnuaLeaveCommand> cmd = context.getCommand();
		List<AnnualLeaveEmpBasicInfo> basicInfoLst = new ArrayList<>();
		List<AnnualLeaveMaxData> maxDataLst = new ArrayList<>();
		
		cmd.parallelStream().forEach(c ->{
			basicInfoLst.add(AnnualLeaveEmpBasicInfo.createFromJavaType(c.getEmployeeId(),
				c.getWorkingDaysPerYear(), c.getWorkingDayBeforeIntro(), c.getGrantTable(), c.getStandardDate()));
			maxDataLst.add(AnnualLeaveMaxData.createFromJavaType(c.getEmployeeId(), c.getMaxTimes(),
					c.getUsedTimes(), c.getMaxMinutes(), c.getUsedMinutes()));
		});
		if(!basicInfoLst.isEmpty()) {
			annLeaBasicInfoRepo.addAll(basicInfoLst);
		}
		
		if(!maxDataLst.isEmpty()) {
			maxDataRepo.addAll(maxDataLst);
		}
		
		return cmd.parallelStream().map(c -> {return new PeregAddCommandResult(c.getEmployeeId());}).collect(Collectors.toList());
	}

}
