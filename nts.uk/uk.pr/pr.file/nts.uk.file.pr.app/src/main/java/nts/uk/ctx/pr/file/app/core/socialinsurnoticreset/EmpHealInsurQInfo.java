package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EmpHealInsurQInfo {
    private String empId;
    private String cid;
    private String healInsurNumber;
    private GeneralDate startDate;
    private GeneralDate endDate;

}
