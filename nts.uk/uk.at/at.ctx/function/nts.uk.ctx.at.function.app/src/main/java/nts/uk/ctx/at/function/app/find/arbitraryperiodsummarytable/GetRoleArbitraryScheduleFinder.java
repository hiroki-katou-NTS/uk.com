package nts.uk.ctx.at.function.app.find.arbitraryperiodsummarytable;


import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleExportRepoAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleWhetherLoginPubImported;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class GetRoleArbitraryScheduleFinder {
    @Inject
    private RoleExportRepoAdapter roleExportRepoAdapter;

    public RoleWhetherLoginPubImported getRoleInfor() {
        // 「ログイン者が担当者か判断する」で就業担当者かチェックする
        return roleExportRepoAdapter.getRoleWhetherLogin();
    }
}
