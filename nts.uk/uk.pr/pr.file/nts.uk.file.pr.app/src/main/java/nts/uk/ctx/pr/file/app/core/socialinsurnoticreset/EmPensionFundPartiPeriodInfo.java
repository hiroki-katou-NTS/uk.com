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
public class EmPensionFundPartiPeriodInfo {
    private String empId;
    private String cid;
    private GeneralDate startDate;
    private GeneralDate endDate;
    private String membersNumber;
}
