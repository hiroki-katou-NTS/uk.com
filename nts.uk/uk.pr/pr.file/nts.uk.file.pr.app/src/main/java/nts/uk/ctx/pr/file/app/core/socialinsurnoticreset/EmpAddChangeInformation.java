package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**住所変更届情報*/
@AllArgsConstructor
@Getter
@Setter
public class EmpAddChangeInformation {
    private String empId;

    /**健康保険加入*/
    private boolean healthInsurance;

    /**厚生年金保険加入*/
    private boolean empPenInsurance;

    /**本人住所変更日*/
    private GeneralDate personAddChangeDate;

    /**被扶養配偶者住所変更日*/
    private GeneralDate spouseAddChangeDate;

    public EmpAddChangeInformation(){
        this.healthInsurance = false;
        this.empPenInsurance = false;
        this.personAddChangeDate = null;
        this.spouseAddChangeDate = null;
    }
}
