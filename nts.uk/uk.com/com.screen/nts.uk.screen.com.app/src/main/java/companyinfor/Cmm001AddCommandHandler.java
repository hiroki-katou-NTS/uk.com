package companyinfor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor.AddDivWorkPlaceDifferInforCommandHandler;
import nts.uk.ctx.command.AddCompanyInforCommandHandler;
import nts.uk.ctx.sys.env.app.command.sysusagesetfinder.AddSysUsageSetCommandHandler;

@Stateless
@Transactional
public class Cmm001AddCommandHandler extends CommandHandler<Cmm001AddCommand>{
	@Inject
	private AddCompanyInforCommandHandler addCom;
	@Inject
	private AddSysUsageSetCommandHandler addSys;
	@Inject
	private AddDivWorkPlaceDifferInforCommandHandler addDiv;
	@Override
	protected void handle(CommandHandlerContext<Cmm001AddCommand> context) {
		Cmm001AddCommand cmm001 = context.getCommand();
		this.addCom.handle(cmm001.getAddComCm());
		this.addDiv.handle(cmm001.getAddDivCm());
		this.addSys.handle(cmm001.getAddSysCm());
	}
}
