package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * DomainService: 職場の承認者履歴を削除する
 *
 * @author chinh.hm
 */
@Stateless
public class DeleteWorkplaceApproverHistoryDomainService {
    // 	[1] 変更する
    public static AtomTask changeHistory(Require require, Approver36AgrByWorkplace deleteHist) {
        val optprevHist = require.getLastHistory(deleteHist.getWorkplaceId(), deleteHist.getPeriod().start().addDays(-1));

        if (optprevHist.isPresent()) {
            val newPeriod = new DatePeriod(optprevHist.get().getPeriod().start(), GeneralDate.max());
            optprevHist.get().setPeriod(newPeriod);
        }
        return AtomTask.of(() -> {
            require.deleteHistory(deleteHist);
            optprevHist.ifPresent(approver36AgrByWorkplace ->
                    require.changeLatestHistory(approver36AgrByWorkplace, approver36AgrByWorkplace.getPeriod().start()));
        });
    }


    public static interface Require {

        //		[R-1] 直前の履歴を取得する :	職場別の承認者（36協定）Repository.指定終了日の履歴を取得する(職場ID,終了日)
        Optional<Approver36AgrByWorkplace> getLastHistory(String workplaceId, GeneralDate endDate);

        // 	[R-2] 履歴を変更する : 	職場別の承認者（36協定）Repository.Update(職場別の承認者（36協定）)
        void changeLatestHistory(Approver36AgrByWorkplace domain, GeneralDate date);

        // 		[R-3] 履歴を削除する: 	職場別の承認者（36協定）Repository.Delete(職場別の承認者（36協定）)
        void deleteHistory(Approver36AgrByWorkplace domain);

    }
}
