package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;


@Stateless
@Transactional
public class RegisterPayrollUnitPriceSettingCommandHandler extends CommandHandler<RegisterPayrollUnitPriceSettingCommand>{

    public static final int MODE_NEW = 0;

    public static final int MODE_UPDATE = 1;

    public static final int MODE_ADD_HISTORY = 2;


    @Inject
    PayrollUnitPriceRepository payrollUnitPriceRepository;

    @Inject
    PayrollUnitPriceHistoryRepository payrollUnitPriceHistoryRepository;

    @Inject
    PayrollUnitPriceSettingRepository payrollUnitPriceSettingRepository;

    @Inject
    private PayrollUnitPriceHistoryService mPayrollUnitPriceHistoryService;

    @Override
    protected void handle(CommandHandlerContext<RegisterPayrollUnitPriceSettingCommand> context) {

        String cid = AppContexts.user().companyId();
        RegisterPayrollUnitPriceSettingCommand command = context.getCommand();
        PayrollUnitPriceCommand payrollUnitPriceCommand = command.getPayrollUnitPriceCommand();
        PayrollUnitPriceHistoryCommand payrollUnitPriceHistoryCommand = command.getPayrollUnitPriceHistoryCommand();
        PayrollUnitPriceSettingCommand   payrollUnitPriceSettingCommand = command.getPayrollUnitPriceSettingCommand();

        String hisId = payrollUnitPriceHistoryCommand.getHisId();
        if(payrollUnitPriceHistoryCommand.getIsMode() == MODE_ADD_HISTORY || payrollUnitPriceHistoryCommand.getIsMode() == MODE_NEW){
            hisId = IdentifierUtil.randomUniqueId();
        }
        YearMonth startYearMonth = new YearMonth(payrollUnitPriceHistoryCommand.getStartYearMonth());
        YearMonth endYearMonth = new YearMonth(payrollUnitPriceHistoryCommand.getEndYearMonth());
        YearMonthHistoryItem history = new YearMonthHistoryItem(hisId,new YearMonthPeriod(startYearMonth,endYearMonth));

        PayrollUnitPrice payrollUnitPrice = new PayrollUnitPrice(cid,payrollUnitPriceCommand.getCode(),payrollUnitPriceCommand.getName());

        PayrollUnitPriceSetting payrollUnitPriceSetting =
                new PayrollUnitPriceSetting(hisId, payrollUnitPriceSettingCommand.getAmountOfMoney(), payrollUnitPriceSettingCommand.getTargetClass(), payrollUnitPriceSettingCommand.getMonthSalaryPerDay(), payrollUnitPriceSettingCommand.getADayPayee(), payrollUnitPriceSettingCommand.getHourlyPay(), payrollUnitPriceSettingCommand.getMonthlySalary(), payrollUnitPriceSettingCommand.getSetClassification(), payrollUnitPriceSettingCommand.getNotes() );

        if(payrollUnitPriceHistoryCommand.getIsMode() == MODE_NEW){
            payrollUnitPriceRepository.add(payrollUnitPrice);
            payrollUnitPriceHistoryRepository.add(history,cid,payrollUnitPriceHistoryCommand.getCode());
            payrollUnitPriceSettingRepository.add(payrollUnitPriceSetting);
        } else if(payrollUnitPriceHistoryCommand.getIsMode() == MODE_UPDATE) {
            payrollUnitPriceRepository.update(payrollUnitPrice);
            mPayrollUnitPriceHistoryService.historyCorrectionProcecessing(cid,hisId,payrollUnitPriceHistoryCommand.getCode(),startYearMonth,endYearMonth);
            payrollUnitPriceHistoryRepository.update(history,cid,payrollUnitPriceHistoryCommand.getCode());
            payrollUnitPriceSettingRepository.update(payrollUnitPriceSetting);
        } else if(payrollUnitPriceHistoryCommand.getIsMode() == MODE_ADD_HISTORY){
            payrollUnitPriceRepository.update(payrollUnitPrice);
            mPayrollUnitPriceHistoryService.historyCorrectionProcecessing(cid,hisId,payrollUnitPriceHistoryCommand.getCode(),startYearMonth,endYearMonth);
            payrollUnitPriceHistoryRepository.add(history,cid,payrollUnitPriceHistoryCommand.getCode());
            payrollUnitPriceSettingRepository.add(payrollUnitPriceSetting);
        }
    }
}
