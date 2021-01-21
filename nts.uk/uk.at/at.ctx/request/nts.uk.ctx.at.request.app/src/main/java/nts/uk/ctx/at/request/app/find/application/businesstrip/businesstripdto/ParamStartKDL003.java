package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParamStartKDL003 {

    private BusinessTripInfoOutputDto businessTripInfoOutputDto;

    private String selectedDate;

}
