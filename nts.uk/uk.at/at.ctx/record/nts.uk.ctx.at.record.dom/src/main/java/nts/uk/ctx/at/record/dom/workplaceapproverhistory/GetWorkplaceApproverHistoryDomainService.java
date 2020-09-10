package nts.uk.ctx.at.record.dom.workplaceapproverhistory;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.shr.com.context.AppContexts;


import java.util.Optional;

/**
 * 	DomainService: 職場別の承認者を取得する
 */
public class GetWorkplaceApproverHistoryDomainService {
    // 	[1] 取得する システム日付時点の所属職場の承認者の履歴項目（36協定）を取得する。
    // ない場合、上位職場の承認者の履歴項目（36協定）を取得する。

    public Optional<Approver36AgrByWorkplace>getWorkplaceApproverHistory(Require require, String employeeId){
        val baseDate = GeneralDate.today();
        val wplHistoryofEmployees = require.getYourWorkplace(employeeId,baseDate);
        if(wplHistoryofEmployees == null){
            return Optional.empty();
        }
        // TODO PHẢI QA CHƠ TÀI LIỆU KO RÕ.
        val optApproverOfWorkplace = require.getApproverHistoryItem(wplHistoryofEmployees.getWorkplaceId());
        if (optApproverOfWorkplace.isPresent()){
            return optApproverOfWorkplace;
        }
        val cid = AppContexts.user().companyId();
        val optTopWorkplaceList = require.getTopWorkplacesOfWorkplace(cid,wplHistoryofEmployees.getWorkplaceId(),baseDate);
        if(optTopWorkplaceList.isPresent()){
            return optTopWorkplaceList;
        }
        return Optional.empty();
    }
    public static interface Require{
        // 	[R-1] 所属職場を取得する 	アルゴリズム.[RQ30]社員所属職場履歴を取得(社員ID,基準日)TODO QA
        Approver36AgrByWorkplace getYourWorkplace(String employeeId, GeneralDate baseDate);

        // 	[R-2] 承認者の履歴項目を取得する 	承認者の履歴項目（36協定）Repository.get(職場ID)
         Optional<Approver36AgrByWorkplace> getApproverHistoryItem(String workplaceId);
        //	[R-3] 上位職場を取得する 	アルゴリズム.[No.569]職場の上位職場を取得する(会社ID,職場ID,基準日) TODO QA

        Optional<Approver36AgrByWorkplace> getTopWorkplacesOfWorkplace(String companyId,String workplaceId,GeneralDate referenceDate);
}
}
