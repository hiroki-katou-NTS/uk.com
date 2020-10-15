package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamChangeDate {

    private BusinessTripInfoOutputDto businessTripInfoOutputDto;

    // 申請
    private ApplicationDto applicationDto;

}
