package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
public class ConfirmOfIndividualSetSttParams {
    private int type;
    private List<InformationEmployeeDto> employeeIds ;
    private String hisId;
    private GeneralDate baseDate;
}
