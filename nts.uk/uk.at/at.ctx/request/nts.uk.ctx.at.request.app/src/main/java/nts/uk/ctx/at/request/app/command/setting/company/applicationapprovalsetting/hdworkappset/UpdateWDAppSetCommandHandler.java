package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdworkappset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetRepository;
/**
 * Update Withdrawal App Set Command Handler
 * @author yennth
 *
 */
@Stateless
public class UpdateWDAppSetCommandHandler extends CommandHandler<WithdrawalAppSetCommand>{
	@Inject
	private WithdrawalAppSetRepository withRep;

	@Override
	protected void handle(CommandHandlerContext<WithdrawalAppSetCommand> context) {
		WithdrawalAppSetCommand data = context.getCommand();
		Optional<WithdrawalAppSet> with = withRep.getWithDraw();
		WithdrawalAppSet withDraw = WithdrawalAppSet.createFromJavaType(data.getCompanyId(), 
				data.getPrePerflex(), data.getBreakTime(), data.getWorkTime(), data.getCheckHdTime(), 
				data.getTypePaidLeave(), data.getWorkChange(), data.getTimeInit(), data.getCheckOut(), 
				data.getPrefixLeave(), data.getUnitTime(), data.getAppSimul(), data.getBounSeg(), 
				data.getDirectDivi(), data.getRestTime());
		if(with.isPresent()){
			withRep.update(withDraw);
		}
		withRep.insert(withDraw);
	}
	
}
