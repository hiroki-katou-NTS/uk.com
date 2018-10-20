package nts.uk.ctx.at.shared.app.command.remainingnumber.rervleagrtremnum;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;

@Stateless
public class UpdateResvLeaCommandHandler extends CommandHandler<UpdateResvLeaRemainCommand> {

	@Inject
	private RervLeaGrantRemDataRepository resvLeaRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateResvLeaRemainCommand> context) {
		UpdateResvLeaRemainCommand c = context.getCommand();
		boolean isHasData = resvLeaRepo.checkValidateGrantDay(c.getEmployeeId(), c.getRvsLeaId(),  c.getGrantDate());
		
		//update No.2844 theo mã bug 102061, ユニーク制約, 社員ID、付与日 #Msg_1456
		if(isHasData) {
			throw new BusinessException("Msg_1456");
		}
		
		ReserveLeaveGrantRemainingData data = ReserveLeaveGrantRemainingData.createFromJavaType(c.getRvsLeaId(),
				c.getEmployeeId(), c.getGrantDate(), c.getDeadline(), c.getExpirationStatus(),
				GrantRemainRegisterType.MANUAL.value, c.getGrantDays(), c.getUseDays(), c.getOverLimitDays(),
				c.getRemainingDays());
		
		resvLeaRepo.update(data);

	}

}
