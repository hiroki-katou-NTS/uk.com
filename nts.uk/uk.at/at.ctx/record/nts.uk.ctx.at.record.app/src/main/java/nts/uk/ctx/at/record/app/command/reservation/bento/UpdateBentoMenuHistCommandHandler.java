package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.UpdateBentoMenuHistService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
@Stateless
public class UpdateBentoMenuHistCommandHandler extends CommandHandler<UpdateBentoMenuHistCommand> {
    @Inject
    private IBentoMenuHistoryRepository bentoMenuHistoryRepository;
    @Override
    protected void handle(CommandHandlerContext<UpdateBentoMenuHistCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
         val cid = AppContexts.user().companyId();
        RequireImpl requei = new RequireImpl(bentoMenuHistoryRepository);
          AtomTask atomTask = UpdateBentoMenuHistService.register(requei,
                new DatePeriod(command.startDatePerio,command.endDatePerio),command.getHistoryId(),cid);
        transaction.execute(()->{
            atomTask.run();
        });
    }

    @AllArgsConstructor
    private static class RequireImpl implements UpdateBentoMenuHistService.Require{
          private IBentoMenuHistoryRepository bentoMenuHistoryRepository;

        @Override
        public Optional<BentoMenuHistory> findByCompanyId(String cid) {
            return  bentoMenuHistoryRepository.findByCompanyId(cid);
        }

        @Override
        public void update(List<DateHistoryItem> item) {
            bentoMenuHistoryRepository.update(BentoMenuHistory.toDomain(AppContexts.user().companyId(),item));

        }

    }
}
