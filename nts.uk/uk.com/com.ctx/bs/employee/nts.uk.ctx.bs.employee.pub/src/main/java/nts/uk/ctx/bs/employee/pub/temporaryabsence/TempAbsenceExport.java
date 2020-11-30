package nts.uk.ctx.bs.employee.pub.temporaryabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TempAbsenceExport {

    private List<EmployeeLeaveHistoryExport> leaveHists;
    private List<TempAbsenceHisItemDto> leaveHistItems;

}
