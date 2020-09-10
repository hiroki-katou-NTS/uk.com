package nts.uk.ctx.at.record.dom.workplaceapproverhistory;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.ApproverItem;
import nts.uk.shr.com.context.AppContexts;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DomainService: 職場別の承認者を取得する
 */
public class GetWorkplaceApproverHistoryDomainService {
    // 	[1] 取得する システム日付時点の所属職場の承認者の履歴項目（36協定）を取得する。
    // ない場合、上位職場の承認者の履歴項目（36協定）を取得する。

    public Optional<ApproverItem> getWorkplaceApproverHistory(Require require, String employeeId) {
        val baseDate = GeneralDate.today();
        val cid = AppContexts.user().companyId();
        // [R-1] use RQ-30 GET LIST Workplace
        val wplHistoryofEmployees = require.getYourWorkplace(employeeId, baseDate);
        if (!wplHistoryofEmployees.isPresent()) {
            //[R-2]
            val approver36AgrByWorkplace = require.getApproverHistoryItem(wplHistoryofEmployees.get().getWorkplaceId(), baseDate);
            if (approver36AgrByWorkplace == null) {
                val listWplId = require.getUpperWorkplace(cid, wplHistoryofEmployees.get().getWorkplaceId(), baseDate);
                if (!listWplId.isEmpty()) {
                    val listApprover36AgrByWorkplace = new ArrayList<Approver36AgrByWorkplace>();
                    listWplId.stream().parallel().forEach(x -> listApprover36AgrByWorkplace.add(require.getApproverHistoryItem(x, baseDate)));
                    if (listApprover36AgrByWorkplace.isEmpty()) {
                        val approverList = new ArrayList<String>();
                        val confirmerList = new ArrayList<String>();
                        listApprover36AgrByWorkplace.stream().parallel().forEach(x -> {
                            approverList.addAll(x.getApproverIds());
                            confirmerList.addAll(x.getConfirmerIds());
                        });
                        return Optional.of(new ApproverItem(approverList, confirmerList));
                    }
                }
            } else {
                return Optional.of(new ApproverItem(approver36AgrByWorkplace.getApproverIds(), approver36AgrByWorkplace.getConfirmerIds()));
            }
        }

        return Optional.empty();

    }

    public static interface Require {
        // 	[R-1] 所属職場を取得する 	アルゴリズム.[RQ30]社員所属職場履歴を取得(社員ID,基準日)
        Optional<SWkpHistRcImported> getYourWorkplace(String employeeId, GeneralDate baseDate);

        // 	[R-2] 承認者の履歴項目を取得する 	承認者の履歴項目（36協定）Repository.get(職場ID)
        Approver36AgrByWorkplace getApproverHistoryItem(String workplaceId, GeneralDate systemDate);

        //	[R-3] 上位職場を取得する 	アルゴリズム.[No.569]職場の上位職場を取得する(会社ID,職場ID,基準日)
        List<String> getUpperWorkplace(String companyID, String workplaceID, GeneralDate date);
    }
}
