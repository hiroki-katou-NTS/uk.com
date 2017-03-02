package nts.uk.ctx.basic.app.command.organization.employment;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.employment.Employment;
import nts.uk.ctx.basic.dom.organization.employment.EmploymentRepository;
import nts.uk.ctx.basic.dom.organization.employment.ManageOrNot;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeleteEmploymentCommandHandler extends CommandHandler<DeleteEmploymentCommand>{
	@Inject
	private EmploymentRepository repository;
	@Override
	protected void handle(CommandHandlerContext<DeleteEmploymentCommand> context) {
		try{
			DeleteEmploymentCommand command = context.getCommand();
			String companyCode = AppContexts.user().companyCode();
			this.repository.remove(companyCode, command.getEmploymentCode());
			//初期表示するのはコードの昇順で先頭になる項目です。
			if(command.getDisplayAtr() == 1){
				List<Employment> lstEmployment = repository.findAllEmployment(companyCode);
				if(lstEmployment != null){
					Employment employmentInf = lstEmployment.get(0);
					employmentInf.setDisplayFlg(ManageOrNot.MANAGE);
					this.repository.update(employmentInf);
				}
			}
		}
		catch(Exception ex){
			throw ex;
		}
	}

}
