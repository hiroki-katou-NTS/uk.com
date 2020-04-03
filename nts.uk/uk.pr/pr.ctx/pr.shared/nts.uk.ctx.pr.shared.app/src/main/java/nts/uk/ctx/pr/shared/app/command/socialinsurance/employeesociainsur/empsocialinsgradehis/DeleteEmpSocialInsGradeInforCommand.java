package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class DeleteEmpSocialInsGradeInforCommand {

    /** 履歴ID */
    @PeregRecordId
    private String historyId;

    /** 社員ID */
    @PeregEmployeeId
    private String sId;
}
