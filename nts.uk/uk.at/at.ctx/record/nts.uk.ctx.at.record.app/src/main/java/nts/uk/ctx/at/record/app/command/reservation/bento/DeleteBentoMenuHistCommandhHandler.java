package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.shr.com.context.AppContexts;

/**
 * 予約構成を削除する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteBentoMenuHistCommandhHandler extends CommandHandler<DeleteBentoMenuHistCommand> {
    @Inject
    private BentoMenuHistRepository bentoMenuHistRepository;

    @Override
    protected void handle(CommandHandlerContext<DeleteBentoMenuHistCommand> commandHandlerContext) {
    	// Get command
        val command = commandHandlerContext.getCommand();
        // Get companyid
        val cid = AppContexts.user().companyId();
    	GeneralDate date = GeneralDate.fromString(command.getStartDate(), "yyyy/MM/dd");
        // 2: 弁当メニュー履歴を取得
        Optional<BentoMenuHistory> opBentoMenuHistory = bentoMenuHistRepository.findByCompanyDate(cid, date.decrease());
        if(opBentoMenuHistory.isPresent()) {
        	// 2.1: 取得した弁当メニュー履歴を更新する
        	BentoMenuHistory oldBentoMenuHistory = opBentoMenuHistory.get();
        	DatePeriod period = new DatePeriod(oldBentoMenuHistory.getHistoryItem().start(), GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"));
        	oldBentoMenuHistory.getHistoryItem().changeSpan(period);
        	bentoMenuHistRepository.update(oldBentoMenuHistory);
        }
        
        // 3: 弁当メニュー履歴を削除
        bentoMenuHistRepository.delete(cid, command.historyId);
    }
}
