package nts.uk.ctx.at.shared.app.command.remainingnumber.annleagrtremnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddAnnLeaListCommandPeregHandler extends CommandHandlerWithResult<List<AddAnnLeaGrantRemnNumPeregCommand>, List<MyCustomizeException>>
implements PeregAddListCommandHandler<AddAnnLeaGrantRemnNumPeregCommand>{
	
	@Inject
	private AnnLeaGrantRemDataRepository annLeaRepo;
	
	@Override
	public String targetCategoryCd() {
		return "CS00037";
	}

	@Override
	public Class<?> commandClass() {
		return AddAnnLeaGrantRemnNumPeregCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(
			CommandHandlerContext<List<AddAnnLeaGrantRemnNumPeregCommand>> context) {
		List<AddAnnLeaGrantRemnNumPeregCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		List<PeregAddCommandResult> result = new ArrayList<>();
		List<AnnualLeaveGrantRemainingData> insertLst = new ArrayList<>();

		Map<String, GeneralDate> emps = cmd.stream().collect(Collectors.toMap(
				AddAnnLeaGrantRemnNumPeregCommand::getEmployeeId, AddAnnLeaGrantRemnNumPeregCommand::getGrantDate));
		Map<String, List<AnnualLeaveGrantRemainingData>> empErrors = annLeaRepo.checkConditionUniqueForAdd(cid, emps);

		cmd.stream().forEach(c -> {
			if (!empErrors.containsKey(c.getEmployeeId())) {
				boolean check = AnnualLeaveGrantRemainingData.validate(c.getGrantDate(), c.getDeadline(),
						c.getGrantDays(), c.getUsedDays(), c.getRemainingDays(), c.grantDateItemName,
						c.deadlineDateItemName);
				if (check) {
					String annLeavId = IdentifierUtil.randomUniqueId();
					AnnualLeaveGrantRemainingData data = AnnualLeaveGrantRemainingData.createFromJavaType(annLeavId,
							c.getEmployeeId(), c.getGrantDate(), c.getDeadline(),
							c.getExpirationStatus() == null ? 1 : c.getExpirationStatus().intValue(),
							GrantRemainRegisterType.MANUAL.value,
							c.getGrantDays() == null ? 0d : c.getGrantDays().doubleValue(),
							c.getGrantMinutes() == null ? null : c.getGrantMinutes().intValue(),
							c.getUsedDays() == null ? 0d : c.getUsedDays().doubleValue(),
							c.getUsedMinutes() == null ? null : c.getUsedMinutes().intValue(), null,
							c.getRemainingDays() == null ? 0d : c.getRemainingDays().doubleValue(),
							c.getRemainingMinutes() == null ? null : c.getRemainingMinutes().intValue(), 0d, null, null,
							null);
					insertLst.add(data);
					result.add(new PeregAddCommandResult(annLeavId));
				}
			}
		});
		
		if (!insertLst.isEmpty()) {
			annLeaRepo.addAll(AppContexts.user().companyId(), insertLst);
		}
		
		if (empErrors.isEmpty()) {

		}
		return new ArrayList<>();
	}
}
