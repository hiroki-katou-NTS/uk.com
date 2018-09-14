package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.SetDaySupportDto;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AddPaymentDateSettingCommandHandler extends CommandHandler<PaymentDateSettingListCommand> {

    @Inject
    SetDaySupportRepository setDaySupportRepository;

    @Inject
    SpecPrintYmSetRepository specPrintYmSetRepository;


    @Override
    protected void handle(CommandHandlerContext<PaymentDateSettingListCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        List<PaymentDateSettingCommand> paymentDateSettingCommands = commandHandlerContext.getCommand().getPaymentDateSettingCommands();
        int processCateNo = paymentDateSettingCommands.get(0).getSetDaySupportCommand().getProcessCateNo();
        int year = paymentDateSettingCommands.get(0).getSetDaySupportCommand().getProcessDate();
        List<SetDaySupport> setDaySupports = setDaySupportRepository.getSetDaySupportByIdAndYear(cid, processCateNo, year);
        if (!setDaySupports.isEmpty()) {
            throw new BusinessException("MsgQ_7");
        }
        for (PaymentDateSettingCommand item : paymentDateSettingCommands) {
            SetDaySupportCommand c1 = item.getSetDaySupportCommand();
            SpecPrintYmSetCommand c2 = item.getSpecPrintYmSetCommand();
            SetDaySupport setDaySupport = new SetDaySupport(cid, processCateNo, c1.getProcessDate(), c1.getCloseDateTime(), c1.getEmpInsurdStanDate(), c1.getClosureDateAccounting(), c1.getPaymentDate(), c1.getEmpExtraRefeDate(), c1.getSocialInsurdStanDate(), c1.getSocialInsurdCollecMonth(), c1.getIncomeTaxDate(), c1.getNumberWorkDay());
            setDaySupportRepository.add(setDaySupport);
            SpecPrintYmSet pecPrintYmSet = new SpecPrintYmSet(cid, processCateNo, c2.getProcessDate(), c2.getPrintDate());
            specPrintYmSetRepository.add(pecPrintYmSet);
        }
    }
}
