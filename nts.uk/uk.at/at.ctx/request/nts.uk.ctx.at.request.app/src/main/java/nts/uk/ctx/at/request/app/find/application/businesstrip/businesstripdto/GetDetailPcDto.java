package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailPcDto {

    private BusinessTripInfoOutputDto businessTripInfoOutputDto;

    private BusinessTripDto businessTripDto;

}
