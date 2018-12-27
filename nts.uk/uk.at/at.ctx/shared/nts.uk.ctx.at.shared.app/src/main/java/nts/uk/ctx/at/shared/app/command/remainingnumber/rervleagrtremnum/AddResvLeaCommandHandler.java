package nts.uk.ctx.at.shared.app.command.remainingnumber.rervleagrtremnum;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.app.find.remainingnumber.rervleagrtremnum.ResvLeaGrantRemNumDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddResvLeaCommandHandler extends CommandHandlerWithResult<AddResvLeaRemainCommand, List<ResvLeaGrantRemNumDto>> {

	@Inject
	private RervLeaGrantRemDataRepository resvLeaRepo;

	protected List<ResvLeaGrantRemNumDto> handle(CommandHandlerContext<AddResvLeaRemainCommand> context) {
		AddResvLeaRemainCommand c = context.getCommand();
		String resvLeaveId = IdentifierUtil.randomUniqueId();
		boolean isHasData = resvLeaRepo.checkValidateGrantDay(c.getEmployeeId(), resvLeaveId,  c.getGrantDate());
		
		//update No.2844 theo mã bug 102061, ユニーク制約, 社員ID、付与日 #Msg_1456
		if(isHasData) {
			throw new BusinessException("Msg_1456");
		}
		
		ReserveLeaveGrantRemainingData data = ReserveLeaveGrantRemainingData.createFromJavaType(
				resvLeaveId , c.getEmployeeId(), c.getGrantDate(), c.getDeadline(),
				c.getExpirationStatus(), GrantRemainRegisterType.MANUAL.value, c.getGrantDays(), c.getUseDays(),
				c.getOverLimitDays(), c.getRemainingDays());
		
		resvLeaRepo.add(data, AppContexts.user().companyId());
		
		List<ReserveLeaveGrantRemainingData> dataList = resvLeaRepo.findNotExp(c.getEmployeeId(), AppContexts.user().companyId());
		return dataList.stream().map(domain -> ResvLeaGrantRemNumDto.createFromDomain(domain))
				.collect(Collectors.toList());
		
	}
}
