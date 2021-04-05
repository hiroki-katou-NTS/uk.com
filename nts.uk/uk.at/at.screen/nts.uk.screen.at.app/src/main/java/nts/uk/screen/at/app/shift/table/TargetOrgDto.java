package nts.uk.screen.at.app.shift.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TargetOrgDto {

    private List<String> workPlaceGroupList;

    private List<String> workPlaceList;

    public static TargetOrgDto getDto(List<ShiftTableRuleForOrganization> domains) {
        List<String> wkplList = domains.stream().filter(x -> x.getTargetOrg().getUnit() == TargetOrganizationUnit.WORKPLACE)
                .map(x -> x.getTargetOrg().getWorkplaceId().get()).collect(Collectors.toList());

        List<String> wkplGroupList = domains.stream().filter(x -> x.getTargetOrg().getUnit() == TargetOrganizationUnit.WORKPLACE_GROUP)
                .map(x -> x.getTargetOrg().getWorkplaceGroupId().get()).collect(Collectors.toList());

        return new TargetOrgDto(wkplList, wkplGroupList);
    }
}
