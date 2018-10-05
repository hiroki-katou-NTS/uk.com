package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ProcessInformationDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ValPayDateSetDto;

@Value
public class ProcessingSegmentCommand {

    private ProcessInformationDto processInformation;

    private ValPayDateSetDto valPayDateSet;



}
