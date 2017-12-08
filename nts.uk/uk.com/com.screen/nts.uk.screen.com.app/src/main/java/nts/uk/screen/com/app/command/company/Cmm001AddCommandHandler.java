package nts.uk.screen.com.app.command.company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.company.app.command.company.AddCompanyInforCommandHandler;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor.AddDivWorkPlaceDifferInforCommandHandler;
import nts.uk.ctx.sys.env.app.command.sysusagesetfinder.AddSysUsageSetCommandHandler;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class Cmm001AddCommandHandler extends CommandHandler<Cmm001AddCommand> {
	@Inject
	private AddCompanyInforCommandHandler addCom;
	@Inject
	private AddSysUsageSetCommandHandler addSys;
	@Inject
	private AddDivWorkPlaceDifferInforCommandHandler addDiv;

	@Override
	protected void handle(CommandHandlerContext<Cmm001AddCommand> context) {
			Cmm001AddCommand cmm001 = context.getCommand();
			Company company = cmm001.getComCm().toDomain(AppContexts.user().contractCode());
			cmm001.getDivCm().setCompanyId(company.getCompanyId());
			cmm001.getSysCm().setCompanyId(company.getCompanyId());
			this.addCom.handle(cmm001.getComCm());
			this.addDiv.handle(cmm001.getDivCm());
			this.addSys.handle(cmm001.getSysCm());
	}
	
	
}
