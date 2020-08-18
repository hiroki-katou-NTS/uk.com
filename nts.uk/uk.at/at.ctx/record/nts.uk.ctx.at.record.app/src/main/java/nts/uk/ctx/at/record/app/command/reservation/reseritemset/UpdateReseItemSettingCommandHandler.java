package nts.uk.ctx.at.record.app.command.reservation.reseritemset;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoUpdateService;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UpdateReseItemSettingCommandHandler extends CommandHandler<UpdateReseItemSettingCommand> {



    @Override
    protected void handle(CommandHandlerContext<UpdateReseItemSettingCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
    }

    @AllArgsConstructor
    private static class RequireImpl implements BentoUpdateService.Require{

        @Override
        public List<BentoMenu> getBentoMenu(List<WorkLocationCode> workLocationCodeList, List<Integer> frameNos) {
            return null;
        }

        @Override
        public void register(List<BentoMenu> bentoMenus) {

        }
    }
}
