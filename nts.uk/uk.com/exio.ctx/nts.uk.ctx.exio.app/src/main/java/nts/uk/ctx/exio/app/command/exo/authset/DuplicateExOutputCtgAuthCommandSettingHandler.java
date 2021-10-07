package nts.uk.ctx.exio.app.command.exo.authset;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.exio.dom.exo.authset.ExOutCtgAuthSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DuplicateExOutputCtgAuthCommandSettingHandler extends CommandHandlerWithResult<DuplicateExOutputCtgAuthSettingCommand, DuplicateExOutputCtgAuthSettingResult> {
    @Inject
    private ExOutCtgAuthSetRepository repo;

    @Override
    protected DuplicateExOutputCtgAuthSettingResult handle(CommandHandlerContext<DuplicateExOutputCtgAuthSettingCommand> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        val command = commandHandlerContext.getCommand();
//
//        Optional<ExOutCtgAuthSet> oldEntity = this.repo.findByCidAndRoleId(companyId, command.getRoleType());
//        oldEntity.ifPresent(e -> {
//            this.repo.add(new ExOutCtgAuthSet(item));
//        });

        return new DuplicateExOutputCtgAuthSettingResult();
    }
}
