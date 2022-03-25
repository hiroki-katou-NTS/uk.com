package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceSFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatInitialDisplay;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.enums.PCSmartPhoneAtt;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceSFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailySItemRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlySRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DuplicateMobileAuthorityDailyFormatCommandHandler extends CommandHandler<DuplicateMobileAuthorityDailyFormatCommand> {
    @Inject
    private AuthorityDailySItemRepository authDailyItemRepository;

    @Inject
    private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

    @Inject
    private AuthorityFormatMonthlySRepository authorityFormatMonthlyRepository;

    @Inject
    private AuthorityDailyPerformanceSFormatRepository authDailyPerMobFormatRepository;

    @Override
    protected void handle(CommandHandlerContext<DuplicateMobileAuthorityDailyFormatCommand> commandHandlerContext) {
        LoginUserContext login = AppContexts.user();
        String companyId = login.companyId();
        val command = commandHandlerContext.getCommand();
        String currentCode = command.getCurrentCode();
        String dailyPerformanceFormatCode = command.getDailyPerformanceFormatCode();
        String dailyPerformanceFormatName = command.getDailyPerformanceFormatName();
        if (this.authDailyPerMobFormatRepository.checkExistCode(companyId,
                new DailyPerformanceFormatCode(dailyPerformanceFormatCode))) {
            throw new BusinessException("Msg_3");
        } else {
            val oldItemDaily = authDailyItemRepository.getAuthorityFormatDailyDetail(companyId, new DailyPerformanceFormatCode(currentCode));
            AuthorityDailyPerformanceSFormat authDaiPerFormat = new AuthorityDailyPerformanceSFormat(
                    companyId,
                    new DailyPerformanceFormatCode(dailyPerformanceFormatCode),
                    new DailyPerformanceFormatName(dailyPerformanceFormatName));

            this.authDailyItemRepository.add(companyId, dailyPerformanceFormatCode, oldItemDaily);
            this.authDailyPerMobFormatRepository.add(authDaiPerFormat);
        }
        AuthorityFormatInitialDisplay authorityFormatInitialDisplay = new AuthorityFormatInitialDisplay(companyId,
                new DailyPerformanceFormatCode(dailyPerformanceFormatCode),
                PCSmartPhoneAtt.SMART_PHONE);
        if (command.getIsDefaultInitial() == 1) {
            if (!this.authorityFormatInitialDisplayRepository.checkExistDataByCompanyId(companyId,
                    PCSmartPhoneAtt.SMART_PHONE)) {
                this.authorityFormatInitialDisplayRepository.add(authorityFormatInitialDisplay);
            } else {
                this.authorityFormatInitialDisplayRepository.update(companyId,
                        new DailyPerformanceFormatCode(dailyPerformanceFormatCode),
                        PCSmartPhoneAtt.SMART_PHONE);
            }
        }
        val oldItemMonthly = authorityFormatMonthlyRepository.getMonthlyDetail(companyId, new DailyPerformanceFormatCode(currentCode));

        if (this.authorityFormatMonthlyRepository.checkExistCode(companyId,
                new DailyPerformanceFormatCode(dailyPerformanceFormatCode))) {
            throw new BusinessException("#Msg_3");
        } else {
            this.authorityFormatMonthlyRepository.add(companyId,
                    new DailyPerformanceFormatCode(dailyPerformanceFormatCode),
                    oldItemMonthly);
        }
    }
}

