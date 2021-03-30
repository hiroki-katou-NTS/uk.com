package nts.uk.screen.at.app.shift.table;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.app.query.shift.table.GetRuleForOrganizationShiftTableQuery;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

import javax.inject.Inject;
import java.util.Optional;

/**
 * 組織のシフト表のルールを取得する
 *
 * @author viet.tx
 */
public class GetRulesForOrganizationShiftTableScreenQuery {

    @Inject
    private GetRuleForOrganizationShiftTableQuery orgShiftTableQuery;

    public ShiftTableRuleForCompanyDto get(OrganizationSelectionListParam request) {
        TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.createFromTargetUnit(
                EnumAdaptor.valueOf(request.getUnitSelected(), TargetOrganizationUnit.class)
                , request.getOrganizationIdSelected());

        Optional<ShiftTableRuleForOrganization> data = orgShiftTableQuery.get(targetOrgIdenInfor);
        if (!data.isPresent()) return new ShiftTableRuleForCompanyDto();

        return ShiftTableRuleForCompanyDto.fromDomain(data.get());
    }
}
