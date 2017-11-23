package nts.uk.ctx.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.company.dom.company.AddInfor;
import nts.uk.ctx.bs.company.dom.company.CompanyInforNew;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * update a company infor item
 * @author yennth
 */
@Stateless
public class UpdateCompanyInforCommandHandler extends CommandHandler<UpdateCompanyInforCommand>{
	@Inject
	private CompanyRepository comRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateCompanyInforCommand> context) {
		UpdateCompanyInforCommand data = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		AddInfor add = null; 
		// company code: 0000
		if(data.getCcd() == "0000"){
			throw new BusinessException("Msg_809");
		}

		if(data.getAddinfor() != null){
			add = data.getAddinfor().toDomainAdd(contractCd, "", data.getCcd());
		}
		CompanyInforNew company =  CompanyInforNew.createFromJavaType(data.getCcd(), data.getName(), 
																		data.getMonth(), 
																		data.getAbolition(), data.getRepname(),
																		data.getRepJob(), data.getComNameKana(), 
																		data.getShortComName(), contractCd, 
																		data.getTaxNo(), add);
		company.validate();
		comRep.updateCom(company);
	}
}
