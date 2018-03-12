package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdappset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSetRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * update time holiday application setting command handler
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateTimeHdAppSetHandler extends CommandHandler<TimeHdAppSetCommand>{
	@Inject
	private TimeHdAppSetRepository timeRep;

	@Override
	protected void handle(CommandHandlerContext<TimeHdAppSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		TimeHdAppSetCommand data = context.getCommand();
		Optional<TimeHdAppSet> time = timeRep.getByCid();
		TimeHdAppSet timeHd = TimeHdAppSet.createFromJavaType(companyId, 
				data.getCheckDay(), data.getUse60h(), data.getUseAttend2(), data.getNameBefore2(), 
				data.getUseBefore(), data.getNameBefore(), data.getActualDisp(), data.getCheckOver(), 
				data.getUseTimeHd(), data.getUseTimeYear(), data.getUsePrivate(), data.getPrivateName(),
				data.getUnionLeave(), data.getUnionName(), data.getUseAfter2(), data.getNameAfter2(), 
				data.getUseAfter(), data.getNameAfter());
		if(time.isPresent()){
			timeRep.update(timeHd);
			return;
		}
		timeRep.insert(timeHd);
	}
}
