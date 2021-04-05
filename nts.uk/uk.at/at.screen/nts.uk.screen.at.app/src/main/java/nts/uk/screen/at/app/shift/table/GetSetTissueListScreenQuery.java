package nts.uk.screen.at.app.shift.table;

import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganizationRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * <<ScreenQuery>> 設定された組織リストを取得する
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM011_スケジュール前準備.B：基本機能制御の設定.メニュー別OCD.設定された組織リストの取得時
 *
 * @author viet.tx
 */
@Stateless
public class GetSetTissueListScreenQuery {

    @Inject
    private ShiftTableRuleForOrganizationRepo repo;

    public TargetOrgDto getTargetOrg() {
        List<ShiftTableRuleForOrganization> data = repo.getAll(AppContexts.user().companyId());
        if (data.isEmpty()) return null;

        return TargetOrgDto.getDto(data);
    }
}
