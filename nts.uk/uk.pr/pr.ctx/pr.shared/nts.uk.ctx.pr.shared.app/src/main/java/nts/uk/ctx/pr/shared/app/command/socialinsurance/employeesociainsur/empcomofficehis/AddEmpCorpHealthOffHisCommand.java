package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Data
public class AddEmpCorpHealthOffHisCommand {

    /**
     * 社員ID
     */
    @PeregEmployeeId
    private String sid;

    /**
     * 開始日
     */
    @PeregItem("IS00788")
    private GeneralDate startDate;

    /**
     * 終了日
     */
    @PeregItem("IS00789")
    private GeneralDate endDate;

    /**
     *社会保険事業所コード
     */
    @PeregItem("IS00790")
    private String socialInsurOfficeCode;

}
