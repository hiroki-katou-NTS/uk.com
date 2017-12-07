package nts.uk.ctx.sys.env.app.command.sysusagesetfinder;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.env.dom.useatr.SysUsageRepository;
import nts.uk.ctx.sys.env.dom.useatr.SysUsageSet;

@Stateless
public class AddSysUsageSetCommandHandler extends CommandHandler<AddSysUsageSetCommand>{
	@Inject
	private SysUsageRepository sysRep;

	@Override
	protected void handle(CommandHandlerContext<AddSysUsageSetCommand> context) {
		AddSysUsageSetCommand data = context.getCommand();
		Optional<SysUsageSet> sys = sysRep.findUsageSet(data.getCompanyId());
		
		if(sys.isPresent()){
			throw new BusinessException("Msg_3");
		}
		
		SysUsageSet sysDom = SysUsageSet.createFromJavaType(data.getCompanyId(),data.getJinji(), 
															data.getShugyo(), data.getKyuyo());
		
		sysRep.insertUsageSet(sysDom);
	}
	
}
