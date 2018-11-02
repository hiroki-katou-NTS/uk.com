package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.StatementItemRangeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class DeleteStatementLayoutHistCommandHandler extends CommandHandler<StatementLayoutHistCommand> {
    @Inject
    private StatementLayoutSetRepository statementLayoutSetRepository;
    @Inject
    private PaymentItemDetailSetRepository paymentItemDetailSetRepository;
    @Inject
    private DeductionItemDetailSetRepository deductionItemDetailSetRepository;
    @Inject
    private StatementItemRangeSettingRepository statementItemRangeSettingRepository;
    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepository;
    @Inject
    private StatementLayoutRepository statementLayoutRepository;

    @Override
    protected void handle(CommandHandlerContext<StatementLayoutHistCommand> context) {
        StatementLayoutHistCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        String code = command.getStatementCode();
        String historyId = command.getHistoryId();

        statementLayoutSetRepository.remove(historyId);
        paymentItemDetailSetRepository.remove(historyId);
        deductionItemDetailSetRepository.remove(historyId);
        statementItemRangeSettingRepository.remove(historyId);

        StatementLayoutHist statementLayoutHist = statementLayoutHistRepository.getLayoutHistByCidAndCode(cid, code);
        if(statementLayoutHist.getHistory().size() > 1) {
            if(statementLayoutHist.latestStartItem().isPresent()) {
                YearMonthHistoryItem lastItem = statementLayoutHist.latestStartItem().get();
                statementLayoutHist.remove(lastItem);
                statementLayoutHistRepository.remove(cid, code, historyId);

                if (statementLayoutHist.latestStartItem().isPresent()) {
                    YearMonthHistoryItem beforeLastItem = statementLayoutHist.latestStartItem().get();
                    statementLayoutHistRepository.update(cid, code, beforeLastItem);
                }
            }
        } else {
            statementLayoutHistRepository.remove(cid, code, historyId);
            statementLayoutRepository.remove(cid, code);
        }
    }
}
