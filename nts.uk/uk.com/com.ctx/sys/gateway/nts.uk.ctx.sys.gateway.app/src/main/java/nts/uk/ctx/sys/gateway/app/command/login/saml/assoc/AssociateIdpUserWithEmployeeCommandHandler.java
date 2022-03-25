package nts.uk.ctx.sys.gateway.app.command.login.saml.assoc;

import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.AssociateIdpUserWithEmployee;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.IdpUserAssociation;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.IdpUserAssociationRepository;
import nts.uk.ctx.sys.gateway.dom.login.sso.saml.assoc.SamlIdpUserName;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AssociateIdpUserWithEmployeeCommandHandler extends CommandHandler<AssociateIdpUserWithEmployeeCommand> {

    @Inject
    private TransactionService transaction;

    @Override
    protected void handle(CommandHandlerContext<AssociateIdpUserWithEmployeeCommand> context) {

        val idpUserName = new SamlIdpUserName(context.getCommand().getIdpUserName());
        String tenantCode = AppContexts.user().contractCode();
        String employeeId = AppContexts.user().employeeId();

        Require require = EmbedStopwatch.embed(new RequireImpl());

        val persist = AssociateIdpUserWithEmployee.associate(require, tenantCode, idpUserName, employeeId);
        transaction.execute(persist);
    }

    public interface Require extends AssociateIdpUserWithEmployee.Require {
    }

    @Inject
    private IdpUserAssociationRepository assocRepo;

    public class RequireImpl implements Require {

        @Override
        public void deleteIdpUserAssociation(String tenantCode, SamlIdpUserName idpUserName) {
            assocRepo.delete(tenantCode, idpUserName);
        }

        @Override
        public void save(IdpUserAssociation assoc) {
            assocRepo.insert(assoc);
        }
    }
}
