package nts.uk.ctx.at.record.ws.approver36agrbycompany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyApproverHistoryAddDto {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 期間
     */

    private GeneralDate starDate;

    private GeneralDate endDate;

    /**
     * 承認者リスト
     */
    private List<String> approveList;
    /**
     * 確認者リスト
     */
    private List<String> confirmedList;
}
