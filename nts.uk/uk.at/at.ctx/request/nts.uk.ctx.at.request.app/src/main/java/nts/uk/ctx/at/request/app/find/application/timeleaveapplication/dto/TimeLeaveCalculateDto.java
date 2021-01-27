package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.CalculationResultDto;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;

import java.util.List;

@Value
public class TimeLeaveCalculateDto {

    private int timeDigestAppType;

    private CalculationResultDto caculationResult;

    private List<ConfirmMsgOutput> confirmMsgOutputs;

}
