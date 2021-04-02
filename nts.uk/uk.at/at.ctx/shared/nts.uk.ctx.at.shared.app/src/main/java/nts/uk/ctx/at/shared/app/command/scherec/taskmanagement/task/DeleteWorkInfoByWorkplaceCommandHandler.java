package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingworkplace.NarrowingByWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 職場を指定して職場別作業情報をすべて削除する
 */
@Stateless
public class DeleteWorkInfoByWorkplaceCommandHandler extends CommandHandler<DeleteWorkInfoByWorkplaceCommand>{
    @Inject
    private NarrowingByWorkplaceRepository narrowingRepository;
    @Override
    protected void handle(CommandHandlerContext<DeleteWorkInfoByWorkplaceCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val cid = AppContexts.user().companyId();
        narrowingRepository.delete(cid,command.getWorkPlaceId());
    }
}
