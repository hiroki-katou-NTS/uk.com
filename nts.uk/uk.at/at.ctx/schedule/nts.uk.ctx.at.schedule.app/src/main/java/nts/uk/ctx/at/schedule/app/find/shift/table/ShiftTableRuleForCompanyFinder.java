package nts.uk.ctx.at.schedule.app.find.shift.table;

import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompany;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompanyRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * <<Query>> 会社のシフト表のルールを取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.App.会社のシフト表のルールを取得する.会社のシフト表のルールを取得する
 *
 * @author viet.tx
 */
@Stateless
public class ShiftTableRuleForCompanyFinder {
    @Inject
    private ShiftTableRuleForCompanyRepo shiftTableRuleForCompanyRepo;

    public ShiftTableRuleDto get() {
        String companyId = AppContexts.user().companyId();
        Optional<ShiftTableRuleForCompany> data = shiftTableRuleForCompanyRepo.get(companyId);
        if (!data.isPresent()) return null;

        return ShiftTableRuleDto.fromDomain(data.get());
    }
}
