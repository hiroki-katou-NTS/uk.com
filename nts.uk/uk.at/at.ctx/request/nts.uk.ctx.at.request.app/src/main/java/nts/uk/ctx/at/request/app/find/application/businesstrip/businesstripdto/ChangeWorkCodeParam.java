package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeWorkCodeParam {

    private String date;

    private BusinessTripInfoOutputDto businessTripInfoOutputDto;

    private String typeCode;

    private String timeCode;

    private Integer startWorkTime;

    private Integer endWorkTime;

}
