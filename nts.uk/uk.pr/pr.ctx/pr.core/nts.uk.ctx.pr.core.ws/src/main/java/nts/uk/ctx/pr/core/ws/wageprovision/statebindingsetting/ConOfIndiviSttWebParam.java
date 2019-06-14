package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.InformationEmployeeDto;

import java.util.List;

@Data
public class ConOfIndiviSttWebParam {
    private List<String> empIds ;
    private GeneralDate baseDate;
}
