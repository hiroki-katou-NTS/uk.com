package nts.uk.screen.at.app.query.cmm024.approver36agrbyworkplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplace;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PerformInitialDisplaysByWorkPlaceScreenDto {
    // 会社別の承認者（36協定）
    public List<Approver36AgrByWorkplace> agrByWorkplaces;

    // List<個人基本情報>
    public List<PersonEmpBasicInfoDto> basicPersonalInfoApprove;

    public List<PersonEmpBasicInfoDto> basicPersonalInfoConfirm;
}
