package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * DomainService :職場の承認者履歴を変更する
 *
 * @author chinh.hm
 */
@Stateless
public class ChangeWorkplaceApproverHistoryDomainService {
    /**
     * [1] 変更する
     * 職場別の承認者（36協定）の履歴を変更して、直前の履歴の終了日を変更する
     */
    public static AtomTask changeWorkplaceApproverHistory(
            Require require,
            GeneralDate startDateBeforeChange,
            Approver36AgrByWorkplace changeHistory
    ) {
        val referenceDate = startDateBeforeChange.addDays(-1);
        val optPrevHist = require.getPrevHistory(changeHistory.getWorkplaceId(), referenceDate);
        if (optPrevHist.isPresent()) {
            val prevHist = optPrevHist.get();
            val periodWithNewEndDate = new DatePeriod(prevHist.getPeriod().start(),
                    changeHistory.getPeriod().start().addDays(-1));
            prevHist.setPeriod(periodWithNewEndDate);

        }
        return AtomTask.of(() -> {
            // Update history item
            require.changeHistory(changeHistory, startDateBeforeChange);
            // Update pre history
            optPrevHist.ifPresent(approver36AgrByWorkplace -> require.changeHistory(approver36AgrByWorkplace,
                    approver36AgrByWorkplace.getPeriod().start()));
        });
    }

    public static interface Require {
        /**
         * [R-1] 直前の履歴を取得する
         * 職場別の承認者（36協定）Repository.指定終了日の履歴を取得する(職場ID,終了日)
         */
        Optional<Approver36AgrByWorkplace> getPrevHistory(String workplaceId, GeneralDate lastDate);

        /**
         * [R-2] 履歴を変更する
         * 職場別の承認者（36協定）Repository.Update(職場別の承認者（36協定）)
         */
        void changeHistory(Approver36AgrByWorkplace domain, GeneralDate date);
    }

}
