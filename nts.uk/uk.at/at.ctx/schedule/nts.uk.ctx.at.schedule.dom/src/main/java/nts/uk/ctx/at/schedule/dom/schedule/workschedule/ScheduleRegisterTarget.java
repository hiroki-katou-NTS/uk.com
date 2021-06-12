package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterImportCode;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ScheduleRegisterTarget {
    
    String employeeId;
    
    GeneralDate date;
    
    ShiftMasterImportCode shiftmasterCode;
}
