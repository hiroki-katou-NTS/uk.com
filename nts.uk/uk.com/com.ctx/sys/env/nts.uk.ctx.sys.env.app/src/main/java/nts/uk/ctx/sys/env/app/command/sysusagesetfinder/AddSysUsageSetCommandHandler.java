package nts.uk.ctx.sys.env.app.command.sysusagesetfinder;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.env.dom.useatr.SysUsageRepository;
import nts.uk.ctx.sys.env.dom.useatr.SysUsageSet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddSysUsageSetCommandHandler extends CommandHandler<AddSysUsageSetCommand>{
	@Inject
	private SysUsageRepository sysRep;

	@Override
	protected void handle(CommandHandlerContext<AddSysUsageSetCommand> context) {
		AddSysUsageSetCommand data = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		Optional<SysUsageSet> sys = sysRep.findUsageSet(data.getCompanyId());
		
		if(sys.isPresent()){
			throw new BusinessException("Msg_3");
		}
		
		SysUsageSet sysDom = SysUsageSet.createFromJavaType(data.getCompanyId(),data.getJinji(), 
															data.getShugyo(), data.getKyuyo());
//		sysDom.createCompanyId(sysDom.getCompanyCode().v(), sysDom.getContractCd().v());
		
		sysRep.insertUsageSet(sysDom);
	}
	
}
