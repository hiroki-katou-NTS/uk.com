package nts.uk.ctx.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.company.dom.company.CompanyInforNew;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * delete a company infor item
 * @author yennth
 *
 */
@Stateless
public class DeleteCompanyInforCommandHandler extends CommandHandler<DeleteCompanyInforCommand>{
	@Inject
	private CompanyRepository comRep;

	@Override
	protected void handle(CommandHandlerContext<DeleteCompanyInforCommand> context) {
		DeleteCompanyInforCommand data = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		Optional<CompanyInforNew> com = comRep.findComByCode(data.getCompanyId());
		if(!com.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		comRep.deleteCom(data.getCompanyId(), contractCd, data.getCompanyCode());
	}
	
}
