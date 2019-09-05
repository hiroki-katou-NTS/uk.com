package nts.uk.ctx.at.shared.app.command.remainingnumber.annualeave;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddAnnuaLeaveListCommandHandler extends CommandHandlerWithResult<List<AddAnnuaLeaveCommand>, List<MyCustomizeException>>
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
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddAnnuaLeaveCommand>> context) {
		List<AddAnnuaLeaveCommand> cmd = context.getCommand();
		List<AnnualLeaveEmpBasicInfo> basicInfoLst = new ArrayList<>();
		List<AnnualLeaveMaxData> maxDataLst = new ArrayList<>();
		
		cmd.stream().forEach(c ->{
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
		
		return new ArrayList<>();
	}

}
