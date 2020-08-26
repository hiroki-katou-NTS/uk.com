package nts.uk.screen.at.app.ksm005.find;

import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * 月間パターンの登録
 */
@Stateless
public class RegisterMonthlyPatternProcessor {

    @Inject
    private MonthlyPatternScreenProcessor monthlyPatternScreenProcessor;

    @Inject
    private MonthlyPatternRepository monthlyPatternRepository;

    public RegisterMonthlyPatternDto registerMonthlyPattern(MonthlyPatternRequestPrams requestPrams) {
        String cid = AppContexts.user().companyId();

        List<MonthlyPattern> monthlyPattern = monthlyPatternRepository.findAll(cid);
        MonthlyPatternDto monthlyPatternDto = monthlyPatternScreenProcessor.findDataMonthlyPattern(requestPrams);
        return  new RegisterMonthlyPatternDto(monthlyPatternDto,monthlyPattern);
    }


}
