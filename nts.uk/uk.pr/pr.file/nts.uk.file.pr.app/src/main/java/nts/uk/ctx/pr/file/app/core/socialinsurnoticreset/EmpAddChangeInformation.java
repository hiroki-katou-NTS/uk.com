package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
@Setter
public class EmpAddChangeInformation {
    private String empId;
    private boolean healthInsurance;
    private boolean empPenInsurance;
    GeneralDate personAddChangeDate;
    GeneralDate spouseAddChangeDate;

    public EmpAddChangeInformation(){
        this.healthInsurance = false;
        this.empPenInsurance = false;
        this.personAddChangeDate = null;
        this.spouseAddChangeDate = null;
    }
}
