package nts.uk.ctx.at.record.dom.monthly.agreement.approver;


import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;

import javax.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * DomainService: 職場別の承認者を取得する
 */
@Stateless
public class GetWorkplaceApproveHistoryDomainService {

    // 	[1] 取得する システム日付時点の所属職場の承認者の履歴項目（36協定）を取得する。
    // ない場合、上位職場の承認者の履歴項目（36協定）を取得する。

    public static Optional<ApproverItem> getWorkplaceApproveHistory(Require require, String employeeId) {

        val baseDate = GeneralDate.today();
        // RQ -30`
        val wkpHistoryEmployees = require.getYourWorkplace(employeeId, baseDate);
        if (!wkpHistoryEmployees.isPresent()) {
            return Optional.empty();
        }
        // [R-2] 承認者の履歴項目を取得する
        val approveOpt = require.getApproveHistoryItem(wkpHistoryEmployees.get().getWorkplaceId(),
                baseDate);
        if (approveOpt.isPresent()) {
            return Optional.of(new ApproverItem(approveOpt.get().getApproverIds(),
                    approveOpt.get().getConfirmerIds()));
        }
        val wplId = wkpHistoryEmployees.get().getWorkplaceId();
        //	[R-3] 上位職場を取得する [No.569]
        val listWplId = require.getUpperWorkplace(wplId, baseDate);
        if (listWplId.isEmpty()) {
            return Optional.empty();
        }
        for (String item : listWplId) {
            val optApprove36AerByWorkplace = require.getApproveHistoryItem(item, baseDate);
            if (optApprove36AerByWorkplace.isPresent()) {
                val rs = new ApproverItem(optApprove36AerByWorkplace.get().getApproverIds(),
                        optApprove36AerByWorkplace.get().getConfirmerIds());
                return Optional.of(rs);
            }

        }
        return Optional.empty();
    }

    public static interface Require {
        // 	[R-1] 所属職場を取得する 	アルゴリズム.[RQ30]社員所属職場履歴を取得(社員ID,基準日)
        //  社員所属職場履歴を取得   SyWorkplaceAdapter
        Optional<SWkpHistRcImported> getYourWorkplace(String employeeId, GeneralDate baseDate);

        // 	[R-2] 承認者の履歴項目を取得する 	承認者の履歴項目（36協定）Repository.get(職場ID,,基準日)
        Optional<Approver36AgrByWorkplace> getApproveHistoryItem(String workplaceId, GeneralDate baseDate);

        //	[R-3] 上位職場を取得する 	アルゴリズム.[No.569]職場の上位職場を取得する(会社ID,職場ID,基準日)会社ID Infused from the APP layer
        List<String> getUpperWorkplace(String workplaceID, GeneralDate date);
    }
}
