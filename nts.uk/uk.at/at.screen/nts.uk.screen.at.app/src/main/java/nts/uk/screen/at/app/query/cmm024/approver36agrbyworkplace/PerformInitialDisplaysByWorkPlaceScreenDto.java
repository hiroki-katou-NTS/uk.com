package nts.uk.screen.at.app.query.cmm024.approver36agrbyworkplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;
import nts.uk.screen.at.app.query.cmm024.approver36agrbycompany.PerformInitialDetail;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PerformInitialDisplaysByWorkPlaceScreenDto {

    /**
     * 会社ID
     */
    private String companyId;

    private String companyName;

    /**
     * 職場ID
     */
    private String workplaceId;

    private String workplaceName;

    private List<PerformInitialDetail> scheduleHistory;
}
