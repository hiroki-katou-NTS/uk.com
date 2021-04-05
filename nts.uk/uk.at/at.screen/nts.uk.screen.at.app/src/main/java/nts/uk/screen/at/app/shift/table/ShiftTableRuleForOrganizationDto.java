package nts.uk.screen.at.app.shift.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftTableRuleForOrganizationDto {
    /**
     * 対象組織
     */
//    private TargetOrgIdenInfor targetOrg;

    private TargetOrgDto targetOrgIdenInfor;
}
