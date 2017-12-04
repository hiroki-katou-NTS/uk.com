package nts.uk.screen.com.app.command.company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.company.dom.company.CompanyInforNew;
import nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor.AddDivWorkPlaceDifferInforCommandHandler;
import nts.uk.ctx.command.AddCompanyInforCommandHandler;
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
		for (int i=1; i<=9999; i++) {
			Cmm001AddCommand cmm001 = context.getCommand();
			cmm001.getComCm().setCcd(postCodeAf(i));
			CompanyInforNew company = cmm001.getComCm().toDomain(AppContexts.user().contractCode());
			cmm001.getDivCm().setCompanyId(company.getCompanyId());
			cmm001.getSysCm().setCompanyId(company.getCompanyId());
	
			this.addCom.handle(cmm001.getComCm());
			this.addDiv.handle(cmm001.getDivCm());
			this.addSys.handle(cmm001.getSysCm());
		}
	}
	
	private String postCodeAf(int i) {
		String postCode = String.format("%04d", i);
		StringBuilder str = new StringBuilder(postCode);
		return str.toString();
	}
}
