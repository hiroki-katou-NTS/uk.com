package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistService;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;

/**
 *  予約構成を追加する
 */
@Stateless
public class BentoMenuHistCommandHandler extends CommandHandler<BentoMenuHistCommand> {
    @Inject
    private IBentoMenuHistoryRepository bentoMenuHistotyRepository;

    @Override
    protected void handle(CommandHandlerContext<BentoMenuHistCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        RequireImpl require = new RequireImpl(bentoMenuHistotyRepository);
        val companyId = AppContexts.user().companyId();
        AtomTask persist = BentoMenuHistService.register(require,
                new DatePeriod(command.getDate(), GeneralDate.max()), companyId);

        transaction.execute(() -> {
            persist.run();

        });
    }

    @AllArgsConstructor
    private static class RequireImpl implements BentoMenuHistService.Require {

        private IBentoMenuHistoryRepository bentoMenuHistoryRepository;

        @Override
        public Optional<BentoMenuHistory> findByCompanyId(String companyId) {
            Optional<BentoMenuHistory> existHist = bentoMenuHistoryRepository.findByCompanyId(companyId);
            return existHist;
        }

        @Override
        public void update(DateHistoryItem item) {
            bentoMenuHistoryRepository.update(BentoMenuHistory.toDomain(AppContexts.user().companyId(), Arrays.asList(item)));
        }

        @Override
        public void add(DateHistoryItem item) {
            bentoMenuHistoryRepository.add(BentoMenuHistory.toDomain(AppContexts.user().companyId(),Arrays.asList(item)));
        }


    }
}

