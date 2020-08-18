package nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkPlaceHist {

    /** The employee id. */
    // 社員ID
    public String employeeId;

    public List<AffiliationPeriodAndWorkplace> lstWkpIdAndPeriod;

}
