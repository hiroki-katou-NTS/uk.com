package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;

import java.util.List;

/**
 * 社員社会保険等級履歴
 */
@Getter
public class EmpSocialInsGradeHis extends AggregateRoot {
    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 期間
     */
    private List<GenericHistYMPeriod> period;

    public EmpSocialInsGradeHis(String employeeId, List<GenericHistYMPeriod> period) {
        this.employeeId = employeeId;
        this.period = period;
    }
}
