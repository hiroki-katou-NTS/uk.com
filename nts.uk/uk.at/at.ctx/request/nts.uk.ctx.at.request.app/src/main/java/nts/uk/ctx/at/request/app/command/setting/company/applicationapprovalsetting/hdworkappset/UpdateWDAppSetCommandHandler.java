package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdworkappset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * Update Withdrawal App Set Command Handler
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateWDAppSetCommandHandler extends CommandHandler<WithdrawalAppSetCommand>{
	@Inject
	private WithdrawalAppSetRepository withRep;

	@Override
	protected void handle(CommandHandlerContext<WithdrawalAppSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		WithdrawalAppSetCommand data = context.getCommand();
//		Optional<WithdrawalAppSet> with = withRep.getWithDraw();
		WithdrawalAppSet withDraw = WithdrawalAppSet.createFromJavaType(companyId, 
				0, data.getBreakTime(),
				data.getWorkTime(), data.getCheckHdTime(), 
				data.getTypePaidLeave(), data.getWorkChange(), data.getTimeInit(), data.getCheckOut(), 
				0, 0, 0, 0, 
				data.getDirectDivi(), data.getRestTime(), data.getOverrideSet(), data.getCalStampMiss());
//		if(with.isPresent()){
			withRep.update(withDraw);
//			return;
//		}
//		withRep.insert(withDraw);
	}
	
}
