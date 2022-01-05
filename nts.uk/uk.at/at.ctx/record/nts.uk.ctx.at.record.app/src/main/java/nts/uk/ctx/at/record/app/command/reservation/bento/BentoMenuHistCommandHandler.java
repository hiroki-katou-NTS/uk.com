package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistService;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.shr.com.context.AppContexts;

/**
 *  予約構成を追加する
 */
@Stateless
public class BentoMenuHistCommandHandler extends CommandHandler<BentoMenuHistCommand> {
    @Inject
    private BentoMenuHistRepository bentoMenuHistRepository;

    @Override
    protected void handle(CommandHandlerContext<BentoMenuHistCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        RequireImpl require = new RequireImpl(bentoMenuHistRepository);
        String companyId = AppContexts.user().companyId();
        DatePeriod period = new DatePeriod(GeneralDate.fromString(command.getStartDate(), "yyyy/MM/dd"), GeneralDate.fromString(command.getEndDate(), "yyyy/MM/dd"));

        AtomTask persist = BentoMenuHistService.register(require, companyId, period);

        transaction.execute(() -> {
            persist.run();
        });
    }

    @AllArgsConstructor
    private class RequireImpl implements BentoMenuHistService.Require {
    	
    	private BentoMenuHistRepository bentoMenuHistRepository;
    	
    	@Override
		public Optional<BentoMenuHistory> getBentoMenu(String companyID, GeneralDate date) {
			return bentoMenuHistRepository.findByCompanyDate(companyID, date);
		}

		@Override
		public void update(BentoMenuHistory bentoMenuHistory) {
			bentoMenuHistRepository.update(bentoMenuHistory);
		}

		@Override
		public void add(BentoMenuHistory bentoMenuHistory) {
			bentoMenuHistRepository.add(bentoMenuHistory);
		}
    }
}

