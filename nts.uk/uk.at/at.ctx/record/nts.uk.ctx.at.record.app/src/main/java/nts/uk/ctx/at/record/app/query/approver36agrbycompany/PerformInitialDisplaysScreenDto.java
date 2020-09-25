package nts.uk.ctx.at.record.app.query.approver36agrbycompany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PerformInitialDisplaysScreenDto {

    // 会社別の承認者（36協定）
    public List<Approver36AgrByCompany> agrByCompany;

    // List<個人基本情報>
    public List<PersonEmpBasicInfoDto> basicPersonalInfoApprove;

    public List<PersonEmpBasicInfoDto> basicPersonalInfoConfirm;
}
