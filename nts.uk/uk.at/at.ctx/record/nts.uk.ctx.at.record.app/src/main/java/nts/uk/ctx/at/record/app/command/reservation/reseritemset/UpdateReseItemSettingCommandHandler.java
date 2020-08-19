package nts.uk.ctx.at.record.app.command.reservation.reseritemset;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoUpdateService;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateReseItemSettingCommandHandler extends CommandHandler<UpdateReseItemSettingCommand> {

    @Inject
    private BentoMenuRepository bentoMenuRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateReseItemSettingCommand> commandHandlerContext) {

        val command = commandHandlerContext.getCommand();
        RequireImpl require = new RequireImpl(bentoMenuRepository);

        Bento bento = new Bento(command.getFrameNo(),
                new BentoName(command.getBenToName()),
                new BentoAmount(command.getAmount1()),
                new BentoAmount(command.getAmount2()),
                new BentoReservationUnitName(command.getUnit()),
                command.isCanBookClosesingTime1(),
                command.isCanBookClosesingTime2(),
                Optional.of(new WorkLocationCode(command.getWorkLocationCode()))
        );

        AtomTask persist = BentoUpdateService.update(require, bento);
        transaction.execute(persist::run);
    }

    @AllArgsConstructor
    private static class RequireImpl implements BentoUpdateService.Require{

        private BentoMenuRepository bentoMenuRepository;

        @Override
        public BentoMenu getBentoMenu(String cid, GeneralDate date) {
            return bentoMenuRepository.getBentoMenuByEndDate(cid,date);
        }

        @Override
        public void register(BentoMenu bentoMenu) {

            bentoMenuRepository.update(bentoMenu);
        }
    }
}
