package nts.uk.ctx.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.company.dom.company.CompanyInforNew;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddCompanyInforCommandHandler extends CommandHandler<AddCompanyInforCommand>{
	@Inject
	private CompanyRepository comRep;

	@Override
	protected void handle(CommandHandlerContext<AddCompanyInforCommand> context) {
		AddCompanyInforCommand data = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		Optional<CompanyInforNew> com = comRep.findComByCode(CompanyInforNew.createCompanyId(data.getCcd(), data.getContractCd()));
		// company existed
		if(com.isPresent()){
			throw new BusinessException("Msg_3");
		}
		CompanyInforNew company =  data.toDomain(contractCd);
		company.validate();
		comRep.insertCom(company);
	}
	
}
