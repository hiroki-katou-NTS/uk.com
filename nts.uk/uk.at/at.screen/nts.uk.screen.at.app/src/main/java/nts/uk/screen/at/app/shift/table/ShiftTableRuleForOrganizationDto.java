package nts.uk.screen.at.app.shift.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRule;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftTableRuleForOrganizationDto {
    /**
     * 対象組織
     */
//    private TargetOrgIdenInfor targetOrg;

    private TargetOrgIdenInforDto targetOrgIdenInfor;

    /**
     * シフト表のルール
     */
    private ShiftTableRuleForCompanyDto shiftTableRuleForCompany;

//    private ShiftTableRule shiftTableRule;
}
