package nts.uk.ctx.core.app.company.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.Company;
import nts.uk.ctx.core.dom.company.CompanyRepository;

@RequestScoped
@Transactional
public class AddCompanyCommandHandler extends AsyncCommandHandler<AddCompanyCommand> {

	@Inject
	private CompanyRepository companyRepository;
	
	@Override
	protected void handle(CommandHandlerContext<AddCompanyCommand> context) {
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Company company = context.getCommand().toDomain();
		company.validate();
		
		this.companyRepository.add(company);
	}

}
