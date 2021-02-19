package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * DomainService:	職場の承認者履歴を追加する
 *
 * @author chinh.hm
 */
@Stateless
public class AddWorkplaceApproverHistoryDomainService {
    //		[1] 追加する : 	職場別の承認者（36協定）の履歴を追加して、直前の履歴の終了日を変更する
    public static AtomTask addNewWorkplaceApproverHistory(Requeire requeire, Approver36AgrByWorkplace histTobeAdd) {
        val itemToBeAdd = Approver36AgrByWorkplace.create(
                histTobeAdd.getWorkplaceId(),
                histTobeAdd.getPeriod(),
                histTobeAdd.getApproverIds(),
                histTobeAdd.getConfirmerIds()
        );
        val optLastHist = requeire.getLatestHistory(histTobeAdd.getWorkplaceId(), GeneralDate.max());
        if (optLastHist.isPresent()) {
            val itemlastHist = optLastHist.get();
            val newPeriod = new DatePeriod(itemlastHist.getPeriod().start(),
                    itemToBeAdd.getPeriod().start().addDays(-1));
            itemlastHist.setPeriod(newPeriod);

        }
        return AtomTask.of(() -> {
            requeire.addHistory(itemToBeAdd);
            optLastHist.ifPresent(approver36AgrByWorkplace -> requeire.changeLatestHistory
                    (approver36AgrByWorkplace, approver36AgrByWorkplace.getPeriod().start()));
        });

    }

    public static interface Requeire {

        // 	[R-1] 最新の履歴を取得する : 	職場別の承認者（36協定）Repository.指定終了日の履歴を取得する(職場ID,終了日)
        Optional<Approver36AgrByWorkplace> getLatestHistory(String workplaceId, GeneralDate endDate);

        // 		[R-2] 履歴を追加する : 	職場別の承認者（36協定）Repository.Insert(職場別の承認者（36協定）)
        void addHistory(Approver36AgrByWorkplace domain);

        //[R-3] 最新の履歴を変更す: 	職場別の承認者（36協定）Repository.Update(職場別の承認者（36協定）)
        void changeLatestHistory(Approver36AgrByWorkplace domain, GeneralDate date);

    }
}
