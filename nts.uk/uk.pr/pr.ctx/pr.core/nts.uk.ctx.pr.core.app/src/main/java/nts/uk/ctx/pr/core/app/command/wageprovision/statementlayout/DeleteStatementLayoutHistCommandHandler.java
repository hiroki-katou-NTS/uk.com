package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.StatementItemRangeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

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

    private static final int END_DATE = 999912;

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
        statementLayoutHistRepository.remove(cid, code, historyId);

        Optional<StatementLayoutHist> statementLayoutHistOptional = statementLayoutHistRepository.getLayoutHistByCidAndCode(cid, code);
        if(statementLayoutHistOptional.isPresent()) {
            StatementLayoutHist statementLayoutHist = statementLayoutHistOptional.get();

            if(statementLayoutHist.latestStartItem().isPresent()) {
                YearMonthHistoryItem lastItem = statementLayoutHist.latestStartItem().get();

                lastItem.newSpan(lastItem.start(), new YearMonth(END_DATE));
                statementLayoutHistRepository.update(cid, code, lastItem);
            }
        } else {
            statementLayoutRepository.remove(cid, code);
        }
    }
}
