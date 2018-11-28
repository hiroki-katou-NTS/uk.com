package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmploymentHisOfEmployee {
    private String sId;
    private GeneralDate startDate;
    private GeneralDate endDate;
    private String employmentCD;
}
