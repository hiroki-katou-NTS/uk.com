package nts.uk.ctx.at.shared.app.command.remainingnumber.rervleagrtremnum;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddResvLeaListCommandPeregHandler extends CommandHandlerWithResult<List<AddResvLeaRemainPeregCommand>, List<MyCustomizeException>>
implements PeregAddListCommandHandler<AddResvLeaRemainPeregCommand>{
	@Inject
	private RervLeaGrantRemDataRepository resvLeaRepo;
	
	@Override
	public String targetCategoryCd() {
		return "CS00038";
	}

	@Override
	public Class<?> commandClass() {
		return AddResvLeaRemainPeregCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddResvLeaRemainPeregCommand>> context) {
		List<AddResvLeaRemainPeregCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		List<PeregAddCommandResult> result = new ArrayList<>();
		List<ReserveLeaveGrantRemainingData> insertLst = new ArrayList<>();

		cmd.stream().forEach(c ->{
			boolean check = ReserveLeaveGrantRemainingData.validate(c.getGrantDate(), c.getDeadline(), c.getGrantDays(),
					c.getUseDays(), c.getRemainingDays() , c.grantDateItemName , c.deadlineDateItemName);
			if (check) {
				String annLeavId = IdentifierUtil.randomUniqueId();
				ReserveLeaveGrantRemainingData data = ReserveLeaveGrantRemainingData.createFromJavaType(annLeavId,
						c.getEmployeeId(), c.getGrantDate(), c.getDeadline(),
						c.getExpirationStatus() == null ? 1 : c.getExpirationStatus().intValue(),
						GrantRemainRegisterType.MANUAL.value,
						c.getGrantDays() == null ? 0d : c.getGrantDays().doubleValue(),
						c.getUseDays() == null ? 0d : c.getUseDays().doubleValue(),
						c.getOverLimitDays() == null ? 0d : c.getOverLimitDays().doubleValue(),
						c.getRemainingDays() == null ? 0d : c.getRemainingDays().doubleValue());
				insertLst.add(data);
				result.add(new PeregAddCommandResult(annLeavId));
			}	
		});
		
		if(!insertLst.isEmpty()) {
			resvLeaRepo.addAll(cid , insertLst);
		}
		return new ArrayList<>();
	}

}
