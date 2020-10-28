package nts.uk.file.at.app.export.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DisplayContentWorkStatusRequest {
    public GeneralDate  startDate;
    public  GeneralDate  endDate;
    public List<EmployeeInfor> employeeInfoList;
    public WorkStatusOutputSettings outputSettings;
    public List<WorkPlaceInfo> workPlaceInfo;
    public int mode;
}
