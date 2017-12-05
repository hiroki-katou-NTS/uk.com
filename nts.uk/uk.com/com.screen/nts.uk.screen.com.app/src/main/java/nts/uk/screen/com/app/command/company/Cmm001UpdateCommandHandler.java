package nts.uk.screen.com.app.command.company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.company.app.command.company.UpdateCompanyInforCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor.UpdateDivWorkPlaceDifferInforCommandHandler;
import nts.uk.ctx.sys.env.app.command.sysusagesetfinder.UpdateSysUsageSetCommandHandler;

@Stateless
@Transactional
public class Cmm001UpdateCommandHandler extends CommandHandler<Cmm001UpdateCommand>{
	@Inject
	private UpdateCompanyInforCommandHandler updateCom;
	
	@Inject
	private UpdateSysUsageSetCommandHandler updateSys;
	
	@Inject
	private UpdateDivWorkPlaceDifferInforCommandHandler updateDiv;
	
	@Override
	protected void handle(CommandHandlerContext<Cmm001UpdateCommand> context) {
		Cmm001UpdateCommand cmm001 = context.getCommand();
		this.updateCom.handle(cmm001.getComCm());
		this.updateDiv.handle(cmm001.getDivCm());
		this.updateSys.handle(cmm001.getSysCm());
	}
}
