package nts.uk.ctx.exio.app.command.exo.authset;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.exio.dom.exo.authset.ExOutCtgAuthSet;
import nts.uk.ctx.exio.dom.exo.authset.ExOutCtgAuthSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DuplicateExOutCtgAuthCommandHandler extends CommandHandlerWithResult<DuplicateExOutCtgAuthCommand, DuplicateExOutputCtgAuthResult> {
    @Inject
    private ExOutCtgAuthSetRepository exOutAuthRepo;

    @Override
    protected DuplicateExOutputCtgAuthResult handle(CommandHandlerContext<DuplicateExOutCtgAuthCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        val command = commandHandlerContext.getCommand();
        val isOverwrite = command.isOverWrite();
        val destinationRoleId = command.getDestinationRoleId();
        boolean isSuccess = false;

        val sourceSettings = this.exOutAuthRepo.findByCidAndRoleId(cid, command.getSourceRoleId());
        if (CollectionUtil.isEmpty(sourceSettings)) return null;
        val destinationSettings = this.exOutAuthRepo.findByCidAndRoleId(cid, destinationRoleId);

        if (isOverwrite) {
            exOutAuthRepo.delete(cid, destinationRoleId);
            sourceSettings.forEach(source -> exOutAuthRepo.add(new ExOutCtgAuthSet(new ExOutCtgAvailabilityPermissionImpl(
                    source.getCompanyId(),
                    destinationRoleId,
                    source.getFunctionNo(),
                    source.isAvailable())))
            );
            isSuccess = true;
        } else {
            for (ExOutCtgAuthSet source : sourceSettings) {
                val destinationOpt = destinationSettings.stream().filter(d -> d.getFunctionNo() == source.getFunctionNo()).findFirst();
                if (destinationOpt.isPresent()) {
                    if (!destinationOpt.get().isAvailable()) {
                        exOutAuthRepo.update(new ExOutCtgAuthSet(new ExOutCtgAvailabilityPermissionImpl(
                                source.getCompanyId(),
                                destinationRoleId,
                                source.getFunctionNo(),
                                source.isAvailable())));
                    }
                } else {
                    exOutAuthRepo.add(new ExOutCtgAuthSet(new ExOutCtgAvailabilityPermissionImpl(
                            source.getCompanyId(),
                            destinationRoleId,
                            source.getFunctionNo(),
                            source.isAvailable())));
                }
            }
            isSuccess = true;
        }

        return new DuplicateExOutputCtgAuthResult(isSuccess, destinationRoleId, isOverwrite);
    }
}
