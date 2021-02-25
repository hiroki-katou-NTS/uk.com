package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailStartScreenInfoDto {

    private boolean result;

    private List<ConfirmMsgOutput> confirmMsgOutputs;

    private BusinessTripInfoOutputDto businessTripInfoOutputDto;

}
