package nts.uk.screen.at.app.ksc001.b;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * ScreenQuery: 初期情報を取得する
 */
@Stateless
public class GetInitialInformationScreenQuery {
    @Inject
    private ClosureRepository closureRepository;
    private static final Integer CLOSURE_ID = 1;

    public InitialInformationDto GetInitialInformation() {
        val rs = new InitialInformationDto();
        val cid = AppContexts.user().companyId();
        val item = closureRepository.findById(cid, CLOSURE_ID);
        if (item.isPresent()) {
            val closure = item.get();
            CurrentMonth closureMonth = closure.getClosureMonth();
            item.ifPresent((Closure opt) -> {
                // アルゴリズム「当月の期間を算出する」を実行する(thực hiện thuật toán 「当月の期間を算出する」)

                DatePeriod datePeriod = ClosureService.getClosurePeriodNws(
                        ClosureService.createRequireM5(closureRepository),
                        CLOSURE_ID,closureMonth.getProcessingYm());

                rs.setStartDate(datePeriod.start());
                rs.setEndDate(datePeriod.end());
            });
        }
        return rs;
    }
}