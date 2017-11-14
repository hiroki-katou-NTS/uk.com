package nts.uk.ctx.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.company.dom.company.AddInfor;
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
		AddInfor add = null; 
		Optional<CompanyInforNew> com = comRep.findComByCode(contractCd, data.getCompanyId(), data.getCcd());
		// company code: 0000
		if(data.getCcd() == "0000"){
			throw new BusinessException("Msg_809");
		}
		// company existed
		if(com.isPresent()){
			throw new BusinessException("Msg_3");
		}
		if(data.getAddinfor() != null){
			add = data.getAddinfor().toDomainAdd(contractCd, data.getCompanyId(), data.getCcd());
		}
		CompanyInforNew company =  CompanyInforNew.createFromJavaType(data.getCcd(), data.getName(), 
																		data.getCompanyId(), data.getMonth(), 
																		data.getAbolition(), data.getRepname(),
																		data.getRepJob(), data.getComNameKana(), 
																		data.getShortComName(), contractCd, 
																		data.getTaxNo(), add);
		company.createCompanyId(company.getCompanyCode().v(), company.getContractCd().v());
		company.validate();
		comRep.insertCom(company);
	}
	
}
