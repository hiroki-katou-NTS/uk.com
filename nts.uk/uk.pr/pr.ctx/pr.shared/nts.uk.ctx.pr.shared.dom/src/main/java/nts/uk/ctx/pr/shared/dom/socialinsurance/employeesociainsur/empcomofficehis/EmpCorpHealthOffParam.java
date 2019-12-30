package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.*;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.HistoryItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpCorpHealthOffParam {

    private String employeeId;

    private DateHistoryItem historyItem;

    private String socialInsurOfficeCode;

}
