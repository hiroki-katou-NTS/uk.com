package nts.uk.ctx.at.record.app.command.standardtime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 全社 screen add
 *
 */
@Stateless
public class AddAgreementTimeOfCompanyCommandHandler extends CommandHandler<AddAgreementTimeOfCompanyCommand> {
	
	@Inject
	private AgreementTimeCompanyRepository agreementTimeCompanyRepository;

	@Inject
	private BasicAgreementSettingRepository basicAgreementSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<AddAgreementTimeOfCompanyCommand> context) {
		AddAgreementTimeOfCompanyCommand command = context.getCommand();
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		
	}

}
