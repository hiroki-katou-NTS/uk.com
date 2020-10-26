package nts.uk.ctx.at.record.dom.monthly.registrationOfApprovers;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Unit;

import java.util.Optional;

/**
 * 履歴を変更可能な開始日か
 *
 * @author Nguyen Huy Quang
 */
public class TimeStartCanChangeHistory {

    /**
     * [1] 確認する
     */
    public static boolean checkUpdate(Require require, Unit unit, Optional<String> workplaceId, GeneralDate dateBeforeChange, GeneralDate dateAfterChange){

        // 1:指定終了日の履歴を取得する(変更前の開始日 - 1)
        GeneralDate endDate = dateBeforeChange.addDays(-1);
        if (unit.value == Unit.COMPANY.value){
            Optional<Approver36AgrByCompany> agrByCompany = require.getByCompanyIdAndEndDate(endDate);
            if (agrByCompany.isPresent() && agrByCompany.get().getPeriod().start().afterOrEquals(dateAfterChange)){
                return false;
            }
            return true;
        }

        // 1.1:指定終了日の履歴を取得する(職場ID,変更前の開始日 - 1)
        if (workplaceId.isPresent()){
            Optional<Approver36AgrByWorkplace> agrByWorkplace = require.getByWorkplaceIdAndEndDate(workplaceId.get(),endDate);
            if (agrByWorkplace.isPresent() && agrByWorkplace.get().getPeriod().start().afterOrEquals(dateAfterChange)){
                return false;
            }
        }

        return true;
    }

    public interface Require {
        //[R-1] 会社の承認者を取得する
        Optional<Approver36AgrByCompany> getByCompanyIdAndEndDate(GeneralDate endDate);

        //[R-2] 職場の承認者を取得する
        Optional<Approver36AgrByWorkplace> getByWorkplaceIdAndEndDate(String workplaceId, GeneralDate endDate);
    }
}
