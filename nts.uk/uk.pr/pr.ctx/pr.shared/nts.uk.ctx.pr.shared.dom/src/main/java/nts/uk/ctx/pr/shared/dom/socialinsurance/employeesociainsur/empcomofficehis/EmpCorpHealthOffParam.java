package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.history.DateHistoryItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpCorpHealthOffParam {

    private String employeeId;

    private DateHistoryItem historyItem;

    private String socialInsurOfficeCode;

}
