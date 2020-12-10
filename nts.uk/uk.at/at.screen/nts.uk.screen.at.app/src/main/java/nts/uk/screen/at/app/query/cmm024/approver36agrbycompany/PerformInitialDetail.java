package nts.uk.screen.at.app.query.cmm024.approver36agrbycompany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PerformInitialDetail {
    /**
     * 期間
     */
    private GeneralDate startDate;

    private GeneralDate endDate;

    // List<個人基本情報>
    public List<PersonInfor> personalInfoApprove;

    public List<PersonInfor> personalInfoConfirm;
}
