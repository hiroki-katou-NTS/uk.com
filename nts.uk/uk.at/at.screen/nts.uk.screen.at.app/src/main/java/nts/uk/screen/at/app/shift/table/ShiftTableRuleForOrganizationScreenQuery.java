package nts.uk.screen.at.app.shift.table;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.app.find.shift.table.ShiftTableRuleForOrganizationFinder;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * <<ScreenQuery>> 組織のシフト表のルールの取得時
 * 組織のシフト表のルールを取得する
 *
 * @author viet.tx
 */
@Stateless
public class ShiftTableRuleForOrganizationScreenQuery {

    @Inject
    private ShiftTableRuleForOrganizationFinder orgShiftTableFinder;

    public ShiftTableRuleForCompanyDto get(OrganizationSelectionListParam request) {
        TargetOrganizationUnit targetOrgUnit = EnumAdaptor.valueOf(request.getUnitSelected(), TargetOrganizationUnit.class);
        TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.createFromTargetUnit(targetOrgUnit, request.getOrganizationIdSelected());

        Optional<ShiftTableRuleForOrganization> data = orgShiftTableFinder.get(targetOrgIdenInfor);
        if (!data.isPresent()) return null;

        return ShiftTableRuleForCompanyDto.fromDomain(data.get());
    }
}
