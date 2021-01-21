package nts.uk.ctx.at.request.dom.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BusinessTripWorkTypes {

    // 年月日
    private GeneralDate date;

    // 勤務種類
    private WorkType workType;

}
