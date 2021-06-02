package nts.uk.ctx.at.schedule.app.find.shift.table;

import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganizationRepo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 組織のシフト表のルールを取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.App.組織のシフト表のルールを取得する.組織のシフト表のルールを取得する
 *
 * @author viet.tx
 */
@Stateless
public class ShiftTableRuleForOrganizationFinder {
    @Inject
    private ShiftTableRuleForOrganizationRepo shiftTableRuleForOrgRepo;

    public Optional<ShiftTableRuleForOrganization> get(TargetOrgIdenInfor targetOrgIdenInfor) {
        return shiftTableRuleForOrgRepo.get(
                AppContexts.user().companyId()
                , targetOrgIdenInfor
        );
    }
}
