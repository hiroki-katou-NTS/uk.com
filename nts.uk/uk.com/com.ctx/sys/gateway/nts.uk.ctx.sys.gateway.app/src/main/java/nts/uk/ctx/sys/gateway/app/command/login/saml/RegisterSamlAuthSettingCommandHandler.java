package nts.uk.ctx.sys.gateway.app.command.login.saml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlOperation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate.SamlOperationRepository;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlResponseValidation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.validate.SamlResponseValidationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Command>> SAML認証設定を登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterSamlAuthSettingCommandHandler extends CommandHandler<RegisterSamlAuthSettingCommand> {

	@Inject
    private SamlOperationRepository operationRepo;
	
	@Inject
	private SamlResponseValidationRepository responseValidRepo;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterSamlAuthSettingCommand> context) {
		RegisterSamlAuthSettingCommand command = context.getCommand();
		List<AtomTask> tasks = new ArrayList<>();
		String tenantCode = AppContexts.user().contractCode();
		SamlOperation operation = command.getSamlOperation();
		
		Optional<SamlOperation> operationOpt = this.operationRepo.find(tenantCode);
		if (operationOpt.isPresent()) {
			tasks.add(AtomTask.of(() -> this.operationRepo.update(operation)));
		} else {
			tasks.add(AtomTask.of(() -> this.operationRepo.insert(operation)));
		}
		
		if (command.isUseSingleSignOn()) {
			if (command.getClientId().isEmpty() && command.getIdpEntityId().isEmpty()
					&& command.getIdpCertificate().isEmpty()) {
				
				tasks.add(AtomTask.of(() -> this.responseValidRepo.remove(tenantCode)));
			}
			
			if (!command.getClientId().isEmpty() && !command.getIdpEntityId().isEmpty()
					&& !command.getIdpCertificate().isEmpty()) {
				SamlResponseValidation validation = command.getSamlResponseValidation();
				
				Optional<SamlResponseValidation> validationOpt = this.responseValidRepo.find(tenantCode);
				
				if (validationOpt.isPresent()) {
					tasks.add(AtomTask.of(() -> this.responseValidRepo.update(validation)));
				} else {
					tasks.add(AtomTask.of(() -> this.responseValidRepo.insert(validation)));
				}
			}
		}
		
		AtomTask.bundle(tasks).run();
	}

}
