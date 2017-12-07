package nts.uk.screen.com.app.command.company;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.company.app.command.company.DeleteCompanyInforCommandHandler;
import nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor.DeleteDivWorkPlaceDifferInforCommandHandler;
import nts.uk.ctx.sys.env.app.command.sysusagesetfinder.DeleteSysUsageSetCommandHandler;
@Stateless
public class Cmm001DeleteCommandHandler extends CommandHandler<Cmm001DeleteCommand>{
	@Inject
	private DeleteCompanyInforCommandHandler delCom;
	@Inject
	private DeleteSysUsageSetCommandHandler delSys;
	@Inject
	private DeleteDivWorkPlaceDifferInforCommandHandler delDiv;
	@Override
	protected void handle(CommandHandlerContext<Cmm001DeleteCommand> context) {
		Cmm001DeleteCommand cmm001 = context.getCommand();
		this.delCom.handle(cmm001.getDelComCm());
		this.delDiv.handle(cmm001.getDelDivCm());
		this.delSys.handle(cmm001.getDelSysCm());
	}
}
