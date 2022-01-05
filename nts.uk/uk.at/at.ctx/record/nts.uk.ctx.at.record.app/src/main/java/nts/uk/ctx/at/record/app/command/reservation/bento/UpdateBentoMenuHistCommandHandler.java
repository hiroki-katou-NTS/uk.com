package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.List;
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
import nts.uk.ctx.at.record.dom.reservation.bento.UpdateBentoMenuHistService;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.shr.com.context.AppContexts;

/**
 * 予約構成を編集する
 */
@Stateless
public class UpdateBentoMenuHistCommandHandler extends CommandHandler<UpdateBentoMenuHistCommand> {
    @Inject
    private BentoMenuHistRepository bentoMenuHistRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateBentoMenuHistCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val cid = AppContexts.user().companyId();
        RequireImpl require = new RequireImpl(bentoMenuHistRepository);
        DatePeriod period = new DatePeriod(GeneralDate.fromString(command.startDatePerio, "yyyy/MM/dd"), GeneralDate.fromString(command.endDatePerio, "yyyy/MM/dd"));
        GeneralDate startDate = GeneralDate.fromString(command.originalStartDate, "yyyy/MM/dd");
        
        AtomTask atomTask = UpdateBentoMenuHistService.register(require, cid, period, startDate);
        transaction.execute(() -> {
            atomTask.run();
        });
    }

    @AllArgsConstructor
    private static class RequireImpl implements UpdateBentoMenuHistService.Require {
    	
    	private BentoMenuHistRepository bentoMenuHistRepository;
    	
    	@Override
		public Optional<BentoMenuHistory> getBentoMenu(String companyID,
				GeneralDate date) {
    		return bentoMenuHistRepository.findByCompanyDate(companyID, date);
		}

		@Override
		public void update(List<BentoMenuHistory> updateBentoMenuHistoryLst) {
			bentoMenuHistRepository.updateLst(updateBentoMenuHistoryLst);
			
		}
    }
}
